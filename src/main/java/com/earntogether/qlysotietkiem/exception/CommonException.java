package com.earntogether.qlysotietkiem.exception;

public class CommonException extends RuntimeException{
    private int statusCode;

    public CommonException(int statusCode, String message){
        super(message);
        this.statusCode = statusCode;
    }
    public int getCode(){
        return this.statusCode;
    }
}
