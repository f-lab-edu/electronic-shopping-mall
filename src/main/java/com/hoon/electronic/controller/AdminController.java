package com.hoon.electronic.controller;

import com.hoon.electronic.domain.LoginDto;
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

@RestController
@RequestMapping("/admins")
@RequiredArgsConstructor
public class AdminController {

    private final MemberService memberService;

    @ApiOperation(value = "관리자 로그인", notes = "관리자 로그인을 진행합니다.")
    @ApiImplicitParam(
            name = "loginDto",
            value = "관리자 로그인 정보를 담은 오브젝트",
            dataType = "LoginDto",
            required = true)
    @PostMapping("/login")
    public HttpStatus login(@RequestBody @Valid LoginDto loginDto, HttpSession session) {
        String email = loginDto.getEmail();
        String password = loginDto.getPassword();

        String loginEmail = memberService.login(email, password);

        HttpSessionUtil.setLoginAdminEmail(session, loginEmail);

        return HttpStatus.OK;
    }

    @ApiOperation(value = "관리자 로그아웃", notes = "관리자 로그아웃을 진행합니다.")
    @GetMapping("/logout")
    public HttpStatus logout(HttpSession session) {
        HttpSessionUtil.logoutAdmin(session);

        return HttpStatus.OK;
    }

}
