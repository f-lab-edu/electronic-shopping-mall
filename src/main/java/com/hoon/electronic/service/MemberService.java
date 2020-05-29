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
        checkDuplicateMember(member);

        String encodedPassword = passwordEncode(member.getPassword());
        member.setPassword(encodedPassword);

        memberRepository.save(member);
        return member.getId();
    }

    private void checkDuplicateMember(Member member) {
        List<Member> findMember = memberRepository.findByEmail(member.getEmail());

        if (!findMember.isEmpty()) {
            throw new IllegalStateException("중복된 회원입니다.");
        }
    }

    private String passwordEncode(String rawPassword) {
        return SHA256Util.encode(rawPassword);
    }
}
