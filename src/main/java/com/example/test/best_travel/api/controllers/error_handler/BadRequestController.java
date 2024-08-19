package com.example.test.best_travel.api.controllers.error_handler;

import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.test.best_travel.api.models.responses.BaseErrorResponse;
import com.example.test.best_travel.api.models.responses.ErrorResponse;
import com.example.test.best_travel.api.models.responses.ErrorsResponse;
import com.example.test.best_travel.util.exceptions.IdNotFoundException;

@RestControllerAdvice
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestController {
    
    @ExceptionHandler(IdNotFoundException.class)
    public BaseErrorResponse handleIdNotFoundException(IdNotFoundException exception){

        return ErrorResponse.builder()
            .error(exception.getMessage())
            .status(HttpStatus.BAD_REQUEST.name())
            .code(HttpStatus.BAD_REQUEST.value())
            .build();
    
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseErrorResponse handleIdNotFoundException(MethodArgumentNotValidException exception){

        ArrayList<String> errors = new ArrayList<String>();
        exception.getAllErrors()
            .forEach(error->errors.add(error.getDefaultMessage()));

        return ErrorsResponse.builder()
            .errors(errors)
            .status(HttpStatus.BAD_REQUEST.name())
            .code(HttpStatus.BAD_REQUEST.value())
            .build();
    
    }
}
