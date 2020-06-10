package com.hoon.electronic.controller;

import com.hoon.electronic.domain.CreateMemberDto;
import com.hoon.electronic.domain.LoginDto;
import com.hoon.electronic.domain.Member;
import com.hoon.electronic.domain.AccountPermissionLevel;
import com.hoon.electronic.service.MemberService;
import com.hoon.electronic.util.HttpSessionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public HttpStatus create(@RequestBody @Valid CreateMemberDto createMemberDto) {
        Member member = Member.builder()
                .email(createMemberDto.getEmail())
                .password(createMemberDto.getPassword())
                .name(createMemberDto.getName())
                .phone(createMemberDto.getPhone())
                .level(AccountPermissionLevel.USER)
                .createDateTime(LocalDateTime.now())
                .build();

        memberService.join(member);

        return HttpStatus.CREATED;
    }

    @PostMapping("/login")
    public HttpStatus login(@RequestBody @Valid LoginDto loginDto, HttpSession session) {
        String email = loginDto.getEmail();
        String password = loginDto.getPassword();

        String loginEmail = memberService.login(email, password);

        HttpSessionUtil.setLoginMemberEmail(session, loginEmail);

        return HttpStatus.OK;
    }

    @GetMapping("/logout")
    public HttpStatus logout(HttpSession session) {
        HttpSessionUtil.logout(session);

        return HttpStatus.OK;
    }

}
