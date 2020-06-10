package com.hoon.electronic.service;

import com.hoon.electronic.domain.Member;
import com.hoon.electronic.repository.MemberRepository;
import com.hoon.electronic.util.SHA256Util;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
@Transactional
class MemberServiceTest {

    MemberService memberService;
    MemberRepository memberRepository;

    @Autowired
    public MemberServiceTest(MemberService memberService, MemberRepository memberRepository) {
        this.memberService = memberService;
        this.memberRepository = memberRepository;
    }

    @Test
    public void 회원가입() throws Exception {
        Member member = Member.builder()
                .email("test@example.com")
                .password("1234")
                .name("test")
                .build();

        Long savedId = memberService.join(member);
        Member findMember = memberRepository.findById(savedId).orElse(null);

        assertEquals(member, findMember);
    }

    @Test
    public void 회원가입_중복회원검증() throws Exception {
        Member member1 = Member.builder()
                .email("test@example.com")
                .password("1234")
                .build();

        Member member2 = Member.builder()
                .email("test@example.com")
                .password("1234")
                .build();

        assertThrows(IllegalStateException.class, () -> {
            memberService.join(member1);
            memberService.join(member2);
        }, "예외가 발생하지 않았다.");
    }

    @Test
    public void 비밀번호_암호화() throws Exception {
        String rawPassword = "1234";

        Member member = Member.builder()
                .email("test@example.com")
                .password(rawPassword)
                .build();

        Long savedId = 0L;

        try {
            savedId = memberService.join(member);
        } catch (NullPointerException e) {
            fail();
        }

        Optional<Member> findMember = memberRepository.findById(savedId);

        String findPassword = findMember.map(Member::getPassword).orElse("nothing");
        String findSalt = findMember.map(Member::getSalt).orElse("nothing");
        String encodedPassword = SHA256Util.encode(rawPassword, findSalt);

        assertEquals(findPassword, encodedPassword);
    }

}