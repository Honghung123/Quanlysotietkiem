package com.earntogether.qlysotietkiem.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
public class ResourceNotFoundException extends RuntimeException implements CommonException{
    private int code;
    private String message;
    @Override
    public int getCode(){
        return this.code;
    }
    @Override
    public String getMessage(){
        return this.message;
    }
}
