package com.wan.tool.exception;

import com.wan.tool.common.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author wan
 * @version 1.0.0
 * @Description 异常捕捉器
 * @createTime 2021年11月29日 14:15:58
 */
@Slf4j
@RestControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(BaseException.class)
    public BaseResponse<Object> handleException(BaseException e) {
        log.info("自定义异常捕捉开始。。");
        return new BaseResponse<>(e.getCode(), e.getMessage());
    }

}
