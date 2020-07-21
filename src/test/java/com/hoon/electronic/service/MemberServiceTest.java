package com.hoon.electronic.service;

import com.hoon.electronic.domain.Member;
import com.hoon.electronic.repository.MemberRepository;
import com.hoon.electronic.util.SHA256Util;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.never;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {
    public static final String email = "test@example.com";
    public static final String rawPassword = "1234";

    @InjectMocks
    MemberService memberService;

    @Mock
    MemberRepository memberRepository;

    @Test
    public void 회원가입() throws Exception {
        Member member = Member.builder()
                .email(email)
                .password(rawPassword)
                .name("test")
                .build();

        memberService.join(member);

        then(memberRepository)
                .should()
                .save(any(Member.class));
    }

    @Test
    public void 회원가입_중복회원검증() throws Exception {
        Member member1 = Member.builder()
                .email(email)
                .password(rawPassword)
                .build();

        Member member2 = Member.builder()
                .email(email)
                .password(rawPassword)
                .build();

        given(memberRepository.findByEmail(email)).willReturn(Arrays.asList(member1, member2));

        try {
            memberService.join(member1);
            fail("예외가 발생해야 한다.");
        } catch (IllegalStateException e) {
            // PASS
        }

        then(memberRepository)
                .should(never())
                .save(any(Member.class));
    }

    @Test
    public void 비밀번호_암호화_salt없음() throws Exception {
        String salt = null;

        try {
            SHA256Util.encode(rawPassword, salt);
            fail("예외가 발생해야 한다.");
        } catch (NullPointerException e) {
            // PASS
        }

        then(memberRepository)
                .should(never())
                .save(any(Member.class));
    }

    @Test
    public void 비밀번호_암호화() throws Exception {
        Member member = Member.builder()
                .email(email)
                .password(rawPassword)
                .build();

        try {
            memberService.join(member);
        } catch (NullPointerException e) {
            fail();
        }

        String findPassword = member.getPassword();
        String findSalt = member.getSalt();
        String encodedPassword = SHA256Util.encode(rawPassword, findSalt);

        then(memberRepository)
                .should()
                .save(any(Member.class));

        assertEquals(findPassword, encodedPassword);
    }

    @Test
    public void 로그인() throws Exception {
        String salt = SHA256Util.generateSalt();
        String encodedPassword = SHA256Util.encode(rawPassword, salt);

        Member member = Member.builder()
                .email(email)
                .password(encodedPassword)
                .salt(salt)
                .build();

        given(memberRepository.findByEmail(email)).willReturn(Collections.singletonList(member));

        String loginEmail = null;
        try {
            loginEmail = memberService.login(email, rawPassword);
        } catch (IllegalStateException e) {
            fail("회원정보가 없습니다.");
        } catch (IllegalArgumentException e) {
            fail("비밀번호가 일치하지 않습니다.");
        }

        assertEquals(email, loginEmail);
    }

    @Test
    public void 로그인_회원정보_없음() throws Exception {
        String failEmail = "fail@example.com";

        given(memberRepository.findByEmail(failEmail)).willReturn(Collections.emptyList());

        try {
            memberService.login(failEmail, rawPassword);
            fail("회원정보가 있는 경우 실패입니다.");
        } catch (IllegalStateException e) {
            // PASS
        }
    }

    @Test
    public void 로그인_비밀번호_불일치() throws Exception {
        String salt = SHA256Util.generateSalt();
        String encodedPassword = SHA256Util.encode(rawPassword, salt);

        Member member = Member.builder()
                .email(email)
                .password(encodedPassword)
                .salt(salt)
                .build();

        given(memberRepository.findByEmail(email)).willReturn(Collections.singletonList(member));

        String failPassword = "fail";
        try {
            memberService.login(email, failPassword);
            fail("비밀번호가 일치하는 경우 실패입니다.");
        } catch (IllegalStateException e) {
            fail("회원정보가 없습니다.");
        } catch (IllegalArgumentException e) {
            // PASS
        }
    }
}