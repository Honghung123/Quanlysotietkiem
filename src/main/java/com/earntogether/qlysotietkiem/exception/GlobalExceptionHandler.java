package com.earntogether.qlysotietkiem.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({ ResourceNotFoundException.class,
            DataNotValidException.class })
    public ResponseEntity<String> handleCommonException(CommonException ex,
                                                        WebRequest request){
        System.out.println("Caught an exception! Bug ne ban oi hahaha");
        String message = ex.getMessage();
        return ResponseEntity.status(ex.getCode()).body(
                String.format("{\"message\":\"%s\"}", message)
        );
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<String> handle(BindException ex) {
        List<ObjectError> listErrors = ex.getBindingResult().getAllErrors();
        String message = listErrors.get(0).getDefaultMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                String.format("{\"message\":\"%s\"}", message)
        );
    }

    // Nên bắt cả Exception.class
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleUnwantedException(Exception e,
                                                          WebRequest request) {
        // Log lỗi ra và ẩn đi message thực sự
        e.printStackTrace();  // Thực tế người ta dùng logger
        String message = e.getMessage();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                        String.format("{\"message\":\"%s\"}", message)
        );
    }
}

