package com.earntogether.qlysotietkiem.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public class DataNotValidException extends RuntimeException implements CommonException {
    private String message;
    private int code;

    public DataNotValidException(String message){
        this.message = message;
        this.code = HttpStatus.BAD_REQUEST.value();
    }

    @Override
    public int getCode() { return this.code; }

    @Override
    public String getMessage(){
        return this.message;
    }
}
