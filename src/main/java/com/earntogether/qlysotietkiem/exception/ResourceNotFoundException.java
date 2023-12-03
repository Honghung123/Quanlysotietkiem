package com.earntogether.qlysotietkiem.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;


public class ResourceNotFoundException extends CommonException{
    public ResourceNotFoundException(String message){
        super(HttpStatus.NOT_FOUND.value(), message);
    }
}
