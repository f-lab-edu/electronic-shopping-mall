package com.hoon.electronic.aop;

import com.hoon.electronic.domain.AccountPermissionLevel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginVerification {
    AccountPermissionLevel level();
}
