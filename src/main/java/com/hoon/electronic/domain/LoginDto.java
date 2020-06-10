package com.hoon.electronic.domain;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
public class LoginDto {

    @NotEmpty
    private String email;

    @NotEmpty
    private String password;

}
