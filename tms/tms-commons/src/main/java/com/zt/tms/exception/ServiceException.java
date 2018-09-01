package com.zt.tms.exception;

/**
 * @author zhangtian
 * @date 2018/8/31
 */

public class ServiceException extends RuntimeException{

    public ServiceException(){}

    public ServiceException(Throwable th) {
        super(th);
    }

    public ServiceException(String message,Throwable th) {
        super(message,th);
    }

    public ServiceException(String message) {
        super(message);
    }
}
