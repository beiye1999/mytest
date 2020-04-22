package com.bdqn.exception;

import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 全局异常配置类
 */
@ControllerAdvice
public class GlobalExceptionHandlerAdvice {
    /**
     * 未授权
     * 程序一旦抛出UnauthorizedException就会触发该方法
     */
    @ExceptionHandler(value = {UnauthorizedException.class})
    public String unauthorized(){
        return "unauthorized";
    }


}
