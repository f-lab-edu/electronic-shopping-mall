package com.hoon.electronic.domain;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
public class CreateMemberDto {

    @NotEmpty
    private String email;

    @NotEmpty
    private String password;

    @NotEmpty
    private String name;

    private String phone;
}