package com.zikrikhairan.springbootadmin.exception;

public class ErrorException extends Exception{
    final String code;
    final String message;
    public ErrorException(String code, String message){
        this.code = code;
        this.message = message;
    }

}
