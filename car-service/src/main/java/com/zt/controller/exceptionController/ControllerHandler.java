package com.zt.controller.exceptionController;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOError;
import java.io.IOException;

/**
 * @author zhangtian
 * @date 2018/7/24
 */
@ControllerAdvice //全局
public class ControllerHandler {

    @ExceptionHandler(IOException.class)
    public String IOException(){
        return "error/500";
    }

}
