package com.thread.exhandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Doug Li
 * @Date 2021/1/30
 * @Describe: 全局异常处理
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Map<String,Object> defaultExceptionHandler(Exception ex){
        log.error("系统异常,异常信息:[{}]",ex.getMessage(),ex);
        return Collections.unmodifiableMap(new HashMap<String, Object>() {{
            put("code", "500");
            put("msg", "系统繁忙,请稍后重试");
        }});
    }

}
