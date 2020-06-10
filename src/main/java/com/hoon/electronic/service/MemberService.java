package com.hoon.electronic.service;

import com.hoon.electronic.domain.Member;
import com.hoon.electronic.repository.MemberRepository;
import com.hoon.electronic.util.SHA256Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Long join(Member member) {
        checkDuplicateMember(member.getEmail());

        passwordEncode(member);

        memberRepository.save(member);
        return member.getId();
    }

    public void checkDuplicateMember(String email) {
        List<Member> findMember = memberRepository.findByEmail(email);

        if (isExistMember(findMember)) {
            throw new IllegalStateException("중복된 회원입니다.");
        }
    }

    public boolean isExistMember(List<Member> member) {
        return !member.isEmpty();
    }

    private void passwordEncode(Member member) {
        String salt = SHA256Util.generateSalt();
        String encodedPassword = SHA256Util.encode(member.getPassword(), salt);

        member.setSalt(salt);
        member.setPassword(encodedPassword);
    }

    @Transactional
    public String login(String email, String password) {
        List<Member> members = memberRepository.findByEmail(email);

        if (isNotExistMember(members)) {
            throw new IllegalStateException("회원정보가 없습니다.");
        }

        Member findMember = members.get(0);
        // 로그인 시 입력한 비밀번호와 DB에 저장된 멤버의 salt 값으로 암호화를 진행
        String encodedPassword = SHA256Util.encode(password, findMember.getSalt());

        if (findMember.isNotMatchPassword(encodedPassword)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        return findMember.getEmail();
    }

    private boolean isNotExistMember(List<Member> members) {
        return members.isEmpty();
    }
}
