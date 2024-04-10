package com.pcl.healthism.web.controller.advice;

import com.pcl.healthism.web.controller.dto.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.annotation.PostConstruct;


/**
 * 1. 全局springmvc controller异常兜底处理，避免造成serverlet容器的线程异常崩溃
 * 2. 全局的线程异常兜底处理，避免造成线程池的不断重新生成新线程，并找不到日志
 * **/
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {
    private static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @PostConstruct
    public void initGlobalTheadUncaughtExceptionHandler() {
        Thread.setDefaultUncaughtExceptionHandler( (t,e) -> {
            logger.error("thread:{} got uncaught exception", t.getName(), e);
        });
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Response handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        logger.error("param resolve failures", e);
        return Response.fail(e.getMessage());
    }

    /**
     * 405 - Method Not Allowed
     */
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Response handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        logger.error("request method not support", e);
        return Response.fail(e.getMessage());
    }

    /**
     * 415 - Unsupported Media FuncType
     */
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public Response handleHttpMediaTypeNotSupportedException(Exception e) {
        logger.error("content type not supported", e);
        return Response.fail(e.getMessage());
    }

    /**
     * 500 - Internal Server Error
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(Exception.class)
    public Response handleException(Exception e) {
        logger.error("application meet with unknown exception", e);
        return Response.fail(e.getMessage());
    }

}
