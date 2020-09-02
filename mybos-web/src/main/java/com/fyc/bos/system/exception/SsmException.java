package com.fyc.bos.system.exception;

/**
 * @Author: fyc
 * @Date: 2020/4/25 16:18
 */
public class SsmException extends Exception {
    //异常消息
    private  String message;

    public SsmException() {
        super();
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public SsmException(String message) {
        this.message = message;
    }
}
