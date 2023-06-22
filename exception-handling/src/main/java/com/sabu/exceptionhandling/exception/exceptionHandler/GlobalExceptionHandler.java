package com.sabu.exceptionhandling.exception.exceptionHandler;

import com.sabu.exceptionhandling.dto.GenericErrorResponse;
import com.sabu.exceptionhandling.exception.MyCustomException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {MyCustomException.class})
    public ResponseEntity<?> myCustomErrorHandler(MyCustomException exception) {
        GenericErrorResponse response = new GenericErrorResponse(exception.getMessage(), "CODE_TEST");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
