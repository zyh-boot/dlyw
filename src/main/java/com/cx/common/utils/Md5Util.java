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

    /**
     *  校验
     *
     * @param oldPassword 前台输入的密码
     * @param defaultPassword 数据库存储的加密密码
     * @return
     */
    public static Boolean decrypt(String oldPassword, String defaultPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(oldPassword,defaultPassword);
    }
}
