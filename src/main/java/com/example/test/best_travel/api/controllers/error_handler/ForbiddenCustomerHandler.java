package com.example.test.best_travel.api.controllers.error_handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.test.best_travel.api.models.responses.BaseErrorResponse;
import com.example.test.best_travel.api.models.responses.ErrorResponse;
import com.example.test.best_travel.util.exceptions.ForbiddenCustomerException;

@RestControllerAdvice
@ResponseStatus(HttpStatus.FORBIDDEN)
public class ForbiddenCustomerHandler {
    
    @ExceptionHandler(ForbiddenCustomerException.class)
    public BaseErrorResponse handleIdNotFoundException(ForbiddenCustomerException exception){

        return ErrorResponse.builder()
            .error(exception.getMessage())
            .status(HttpStatus.FORBIDDEN.name())
            .code(HttpStatus.FORBIDDEN.value())
            .build();
    
    }
}
