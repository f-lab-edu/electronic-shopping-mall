package com.hoon.electronic.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA256Util {
    public static String encode(String msg) {
        String result = "";
        MessageDigest md;

        try {
            md = MessageDigest.getInstance("SHA-256");
            md.update(msg.getBytes());

            byte[] data = md.digest();

            StringBuilder sb = new StringBuilder();

            for (byte b : data) {
                sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
            }

            result = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("현재 환경에서 사용할 수 없는 알고리즘입니다.");
        }

        return result;
    }
}
