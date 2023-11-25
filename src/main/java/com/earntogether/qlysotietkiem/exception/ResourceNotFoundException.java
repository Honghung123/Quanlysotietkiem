package com.earntogether.qlysotietkiem.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public class ResourceNotFoundException extends RuntimeException implements CommonException{
    private int code;
    private String message;

    public ResourceNotFoundException(String message){
        this.message = message;
        this.code = HttpStatus.NOT_FOUND.value();
    }

    @Override
    public int getCode(){
        return this.code;
    }
    @Override
    public String getMessage(){
        return this.message;
    }
}
