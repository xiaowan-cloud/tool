package com.wan.tool.exception;

import lombok.extern.slf4j.Slf4j;

/**
 * @author wan
 * @version 1.0.0
 * @Description 异常处理类
 * @createTime 2021年08月10日 14:03:27
 */
@Slf4j
public class BaseException extends RuntimeException{

    private static final long serialVersionUID = 4636592210364844533L;

    private Integer code;
    private String message;

    public BaseException() {
        log.info("产生异常");
    }

    public BaseException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
