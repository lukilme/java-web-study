package com.example.servlet.util;

import at.favre.lib.crypto.bcrypt.BCrypt;

public final class PasswordUtil {

    private PasswordUtil() {}

    public static String hash(char[] password) {
        if (password == null) throw new IllegalArgumentException("password required");
        String pw = new String(password);
        return BCrypt.withDefaults().hashToString(12, pw.toCharArray());
    }

    public static boolean verify(char[] password, String hash) {
        if (password == null || hash == null) return false;
        BCrypt.Result result = BCrypt.verifyer().verify(password, hash);
        return result.verified;
    }
}
