package com.briup.product_source.exception;

import com.briup.product_source.result.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        Result result = null;
        if (e instanceof ServiceException) {
            result = Result.failure(((ServiceException) e).getResultCode());
        } else {
            result = Result.failure(500, "服务器意外错误：" + e.getMessage());
        }
        return result;
    }
}
