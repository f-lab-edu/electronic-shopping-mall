package com.hoon.electronic.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class SHA256Util {
    public static String encode(String password, String salt) {
        String result = "";

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt.getBytes());
            md.update(password.getBytes());

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

    public static String generateSalt() {
        Random random = new Random();

        byte[] salt = new byte[8];
        random.nextBytes(salt);

        StringBuilder sb = new StringBuilder();
        for (byte b : salt) {
            sb.append(String.format("%02x", b));
        }

        return sb.toString();
    }

}
