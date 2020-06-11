package com.hoon.electronic.controller;

import com.hoon.electronic.domain.CreateMemberDto;
import com.hoon.electronic.domain.Member;
import com.hoon.electronic.domain.AccountPermissionLevel;
import com.hoon.electronic.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
