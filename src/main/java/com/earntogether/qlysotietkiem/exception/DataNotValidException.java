package com.earntogether.qlysotietkiem.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

public class DataNotValidException extends CommonException {
    public DataNotValidException(String message){
        super(HttpStatus.BAD_REQUEST.value(), message);
    }
}
