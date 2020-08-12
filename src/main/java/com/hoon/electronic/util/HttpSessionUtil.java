package com.hoon.electronic.util;

import javax.servlet.http.HttpSession;

public class HttpSessionUtil {
    public static final String MEMBER_SESSION_KEY = "loginMember";
    public static final String ADMIN_SESSION_KEY = "loginAdmin";

    public static void setLoginMemberEmail(HttpSession session, String email) {
        session.setAttribute(MEMBER_SESSION_KEY, email);
    }

    public static String getLoginMemberEmail(HttpSession session) {
        return (String) session.getAttribute(MEMBER_SESSION_KEY);
    }

    public static void logoutMember(HttpSession session) {
        session.removeAttribute(MEMBER_SESSION_KEY);
    }

    public static void setLoginAdminEmail(HttpSession session, String email) {
        session.setAttribute(ADMIN_SESSION_KEY, email);
    }

    public static String getLoginAdminEmail(HttpSession session) {
        return (String) session.getAttribute(ADMIN_SESSION_KEY);
    }

    public static void logoutAdmin(HttpSession session) {
        session.removeAttribute(ADMIN_SESSION_KEY);
    }
}
