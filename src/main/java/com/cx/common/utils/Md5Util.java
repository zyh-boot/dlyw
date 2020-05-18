package com.cx.common.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author Administrator·
 */
public class Md5Util {

    private static final String ALGORITH_NAME = "md5";
    private static final int HASH_ITERATIONS = 5;

    protected Md5Util() {

    }


    public static String encrypt(String username, String defaultPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(defaultPassword);
    }
}
