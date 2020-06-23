package com.hoon.electronic.aop;

import com.hoon.electronic.util.HttpSessionUtil;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;

@Aspect
@Component
public class LoginCheckAspect {

    @Before("@annotation(com.hoon.electronic.aop.LoginType) && @annotation(loginType)")
    public void loginCheck(LoginType loginType) {
        HttpSession session = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession();

        switch (loginType.level()) {
            case ADMIN:
                verifyAdminSession(session);
                break;
            case MEMBER:
                verifyMemberSession(session);
                break;
        }
    }

    private void verifyMemberSession(HttpSession session) {
        String loginMemberEmail = HttpSessionUtil.getLoginMemberEmail(session);

        if (loginMemberEmail == null) {
            throw new IllegalStateException("로그인(유저)이 필요합니다.");
        }
    }

    private void verifyAdminSession(HttpSession session) {
        String loginAdminEmail = HttpSessionUtil.getLoginAdminEmail(session);

        if (loginAdminEmail == null) {
            throw new IllegalStateException("로그인(관리자)이 필요합니다.");
        }
    }

}
