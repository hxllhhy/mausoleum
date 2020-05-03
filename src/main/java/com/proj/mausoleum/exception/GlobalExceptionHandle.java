package com.proj.mausoleum.exception;

import com.proj.mausoleum.result.CodeMsg;
import com.proj.mausoleum.result.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandle {

    // 捕获全局异常,处理所有不可知的异常
    @ExceptionHandler(value = Exception.class)
    public Result exceptionHandler(HttpServletRequest request, Exception e) {
        return Result.error(CodeMsg.SERVER_ERROR);
    }
}
