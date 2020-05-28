package com.hoon.electronic.service;

import com.hoon.electronic.domain.Member;
import com.hoon.electronic.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
                .build();

        Member member2 = Member.builder()
                .email("test@example.com")
                .build();

        assertThrows(IllegalStateException.class, () -> {
            memberService.join(member1);
            memberService.join(member2);
        }, "예외가 발생하지 않았다.");
    }
}