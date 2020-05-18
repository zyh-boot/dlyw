package com.cx.common.exception;

/**
 * 系统内部异常
 *
 * @author Administrator·
 */
public class CommonException extends Exception {

    private static final long serialVersionUID = -994962710559017255L;

    public CommonException(String message) {
        super(message);
    }
}
