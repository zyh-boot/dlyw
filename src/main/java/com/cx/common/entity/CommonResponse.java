package com.cx.common.entity;

import org.springframework.http.HttpStatus;

import java.util.HashMap;

/**
 * @author Administrator·
 */
public class CommonResponse extends HashMap<String, Object> {

    private static final long serialVersionUID = -8713837118340960775L;


    public CommonResponse() {
    }

    public CommonResponse(int code) {
        this.put("code", code);
    }

    public CommonResponse(int code, Object content) {
        this.put("code", code);
        this.put("data", content);
    }

    public CommonResponse code(HttpStatus status) {
        this.put("code", status.value());
        return this;
    }

    public CommonResponse code(int status) {
        this.put("code", status);
        return this;
    }

    public CommonResponse message(String message) {
        this.put("message", message);
        return this;
    }

    public CommonResponse data(Object data) {
        this.put("data", data);
        return this;
    }

    public CommonResponse success() {
        this.code(HttpStatus.OK);
        return this;
    }

    public CommonResponse successInt() {
        this.code(Constant.STATE_0);
        return this;
    }

    public CommonResponse fail() {
        this.code(HttpStatus.INTERNAL_SERVER_ERROR);
        return this;
    }

    @Override
    public CommonResponse put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
