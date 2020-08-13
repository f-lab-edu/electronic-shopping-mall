package com.hoon.electronic.controller;

import com.hoon.electronic.domain.AccountPermissionLevel;
import com.hoon.electronic.domain.CreateMemberDto;
import com.hoon.electronic.domain.LoginDto;
import com.hoon.electronic.domain.Member;
import com.hoon.electronic.service.MemberService;
import com.hoon.electronic.util.HttpSessionUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
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

    @ApiOperation(value = "회원가입", notes = "회원가입을 진행합니다.")
    @ApiImplicitParam(
            name = "createMemberDto",
            value = "회원가입 정보를 담은 오브젝트",
            dataType = "CreateMemberDto",
            required = true)
    @PostMapping
    public HttpStatus create(@RequestBody @Valid CreateMemberDto createMemberDto) {
        Member member = Member.builder()
                .email(createMemberDto.getEmail())
                .password(createMemberDto.getPassword())
                .name(createMemberDto.getName())
                .phone(createMemberDto.getPhone())
                .level(AccountPermissionLevel.MEMBER)
                .createDateTime(LocalDateTime.now())
                .build();

        memberService.join(member);

        return HttpStatus.CREATED;
    }

    @ApiOperation(value = "회원 로그인", notes = "회원 로그인을 진행합니다.")
    @ApiImplicitParam(
            name = "loginDto",
            value = "회원 로그인 정보를 담은 오브젝트",
            dataType = "LoginDto",
            required = true)
    @PostMapping("/login")
    public HttpStatus login(@RequestBody @Valid LoginDto loginDto, HttpSession session) {
        String email = loginDto.getEmail();
        String password = loginDto.getPassword();

        String loginEmail = memberService.login(email, password);

        HttpSessionUtil.setLoginMemberEmail(session, loginEmail);

        return HttpStatus.OK;
    }

    @ApiOperation(value = "회원 로그아웃", notes = "회원 로그아웃을 진행합니다.")
    @GetMapping("/logout")
    public HttpStatus logout(HttpSession session) {
        HttpSessionUtil.logoutMember(session);

        return HttpStatus.OK;
    }

}
