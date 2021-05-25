package com.capgemini.exceptions;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.service.Response;

@RestControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public Response resourceNotFoundException(ResourceNotFoundException ex) {
        return new ResponseBuilder().code("404").description(ex.getMessage()).build();
    }
    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public Response resourceAlreadyExistsException(ResourceAlreadyExistsException ex) {
        return new ResponseBuilder().code("409").description(ex.getMessage()).build();
    }
}
