package com.example.employee.exception;

import com.example.employee.dto.ValidationError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler({ValidationError.class})
    public ResponseEntity<ErrorResponse> handleConflictException(ValidationError error, WebRequest request) {
        return new ResponseEntity<>(new ErrorResponse(error.getCode(), error.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
