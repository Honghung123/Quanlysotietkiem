package com.earntogether.qlysotietkiem.exception;

import com.earntogether.qlysotietkiem.model.AppResponse;
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
    public ResponseEntity<AppResponse> handleCommonException(CommonException ex,
                                                             WebRequest request){
        System.out.println("Caught an exception! Bug ne ban oi hahaha");
        return ResponseEntity.status(ex.getCode()).body(
                new AppResponse(ex.getCode(), ex.getMessage()));
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<AppResponse> handle(BindException ex) {
        List<ObjectError> listErrors = ex.getBindingResult().getAllErrors();
        String message = listErrors.get(0).getDefaultMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new AppResponse(HttpStatus.BAD_REQUEST.value(), message));
    }

    // Nên bắt cả Exception.class
    @ExceptionHandler(Exception.class)
    public ResponseEntity<AppResponse> handleUnwantedException(
                                            Exception ex, WebRequest request) {
        // Log lỗi ra và ẩn đi message thực sự
        ex.printStackTrace();  // Thực tế người ta dùng logger
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                       new AppResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage()));
    }
}

