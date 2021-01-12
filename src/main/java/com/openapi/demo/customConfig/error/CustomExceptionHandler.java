package com.openapi.demo.customConfig.error;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.NativeWebRequest;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ErrorMessage handlerCustomException(CustomException e, NativeWebRequest request) {
        return new ErrorMessage(e.getCode(), e.getMessage(), e, request);
    }

    @ExceptionHandler(RuntimeException.class)
    public ErrorMessage processRuntimeException(RuntimeException e, NativeWebRequest request) {
        e.printStackTrace();
        return new ErrorMessage("捕获RuntimeException:" + e.getMessage(), e, request);
    }

    @ExceptionHandler(Exception.class)
    public ErrorMessage processException(Exception e, NativeWebRequest request) {
        e.printStackTrace();
        return new ErrorMessage("捕获Exception:" + e.getMessage(), e, request);
    }
}
