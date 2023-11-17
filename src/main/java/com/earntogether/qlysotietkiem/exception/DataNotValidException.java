package com.earntogether.qlysotietkiem.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class DataNotValidException extends RuntimeException implements CommonException {
    private int code;
    private String message;

    @Override
    public int getCode() { return this.code; }

    @Override
    public String getMessage(){
        return this.message;
    }
}
