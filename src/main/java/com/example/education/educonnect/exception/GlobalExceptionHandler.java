package com.example.education.educonnect.exception;

import com.example.education.educonnect.model.ExceptionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(Conflict.class)
    public ExceptionModel handleConflictException() {
        ExceptionModel exception = new ExceptionModel();
        exception.setStatus(409);
        exception.setError("Conflict");
        exception.setMessage("Email already exists!");
        return exception;
    }

}
