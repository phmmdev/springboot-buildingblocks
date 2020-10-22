package com.stacksimplify.restservices.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import javax.xml.ws.Response;
import java.util.Date;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler
{

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        CustomErrorDetails customErrorDetails =  new CustomErrorDetails(new Date(),
                "From MethodArgumentNotValid Exception GHE",
                ex.getLocalizedMessage());

        return new ResponseEntity<>(customErrorDetails, HttpStatus.BAD_REQUEST);

    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        CustomErrorDetails customErrorDetails =  new CustomErrorDetails(new Date(),
                "From MethodArgumentNotValid Exception GHE - Method not allowed ",
                ex.getLocalizedMessage());

        return new ResponseEntity<>(customErrorDetails, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(UserNameNotFoundException.class)
    public ResponseEntity<Object> handleUserNameNotFoundException(UserNameNotFoundException exception, WebRequest webRequest)
    {
        CustomErrorDetails customErrorDetails =  new CustomErrorDetails(new Date(), exception.getMessage(),
                webRequest.getDescription(false));


        return new ResponseEntity<>(customErrorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public final ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException exception, WebRequest webRequest)
    {
        CustomErrorDetails customErrorDetails =  new CustomErrorDetails(new Date(), exception.getMessage(),
                webRequest.getDescription(false));


        return new ResponseEntity<>(customErrorDetails, HttpStatus.BAD_REQUEST);
    }
}
