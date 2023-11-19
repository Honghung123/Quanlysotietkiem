package com.earntogether.qlysotietkiem.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({ ResourceNotFoundException.class,
            DataNotValidException.class })
    public ResponseEntity<String> handleCommonException(CommonException e,
                                                        WebRequest request){
        System.out.println("Caught an exception! Bug ne ban oi hahaha");
        return ResponseEntity.status(e.getCode())
                .body(String.format("{\"message\" : \"%s\"}", e.getMessage()));
    }

    // Nên bắt cả Exception.class
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleUnwantedException(Exception e,
                                                          WebRequest request) {
        // Log lỗi ra và ẩn đi message thực sự
        String message = "Leu leu, lai BUG nua! check lai xem :D";
        e.printStackTrace();  // Thực tế người ta dùng logger
        return ResponseEntity.status(500)
                .body(String.format("{\"message\" : \"%s\"}", message));
    }
}

