package com.hoon.electronic.util;

import javax.servlet.http.HttpSession;

public class HttpSessionUtil {
    public static final String MEMBER_SESSION_KEY = "loginMember";

    public static void setLoginMemberEmail(HttpSession session, String email) {
        session.setAttribute(MEMBER_SESSION_KEY, email);
    }

    public static String getLoginMemberEmail(HttpSession session) {
        return (String) session.getAttribute(MEMBER_SESSION_KEY);
    }

    public static void logout(HttpSession session) {
        session.removeAttribute(MEMBER_SESSION_KEY);
    }
}
