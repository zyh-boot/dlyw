package com.cx.common.entity;

import org.springframework.http.HttpStatus;

import java.util.HashMap;

/**
 * @author Administrator·
 */
public class ApsResponse extends HashMap<String, Object> {

    private static final long serialVersionUID = -8713837118340960775L;


    public ApsResponse() {
    }

    public ApsResponse(int code) {
        this.put("code", code);
    }

    public ApsResponse(int code, Object content) {
        this.put("code", code);
        this.put("data", content);
    }

    public ApsResponse code(HttpStatus status) {
        this.put("code", status.value());
        return this;
    }

    public ApsResponse code(int status) {
        this.put("code", status);
        return this;
    }

    public ApsResponse message(String message) {
        this.put("message", message);
        return this;
    }

    public ApsResponse data(Object data) {
        this.put("data", data);
        return this;
    }

    public ApsResponse success() {
        this.code(HttpStatus.OK);
        return this;
    }

    public ApsResponse successInt() {
        this.code(0);
        return this;
    }

    public ApsResponse fail() {
        this.code(HttpStatus.INTERNAL_SERVER_ERROR);
        return this;
    }

    @Override
    public ApsResponse put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
