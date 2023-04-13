package com.jas.handler;

import com.jas.dto.ApiErrorResponse;
import com.jas.exceptions.*;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;
import java.util.stream.Collectors;

@RestControllerAdvice
public class UserManagementExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request){
        String ERROR_MESSAGE = ex.getMessage();
        try {
            ERROR_MESSAGE = ex.getBindingResult().getFieldErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(", "));
        } catch (Exception e) {
            logger.error("Error constructing error message", e);
        }

        logger.error(ERROR_MESSAGE, ex);
        ApiErrorResponse errorResponse = getErrorResponse(HttpStatus.BAD_REQUEST, ERROR_MESSAGE);
        return handleExceptionInternal(ex, errorResponse, headers, HttpStatus.BAD_REQUEST,
                request);
    }

    @ExceptionHandler(DepartmentAlreadyExistsException.class)
    public ResponseEntity<Object> handleDepartmentAlreadyExistsException(DepartmentAlreadyExistsException exception,
                                                                       WebRequest webRequest) {
        final String ERROR_MESSAGE = exception.getMessage();
        logger.error(ERROR_MESSAGE, exception);
        ApiErrorResponse errorResponse = getErrorResponse(HttpStatus.BAD_REQUEST, ERROR_MESSAGE);
        return handleExceptionInternal(exception, errorResponse, new HttpHeaders(),
                HttpStatus.BAD_REQUEST, webRequest);
    }

    @ExceptionHandler(DepartmentNotFoundException.class)
    public ResponseEntity<Object> handleDepartmentNotFoundException(DepartmentNotFoundException e, WebRequest webRequest)
    {
        final String ERROR_MESSAGE = e.getMessage();
        logger.error(ERROR_MESSAGE, e);
        ApiErrorResponse errorResponse = getErrorResponse(HttpStatus.NOT_FOUND, ERROR_MESSAGE);
        return handleExceptionInternal(e, errorResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, webRequest);
    }



    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAnyException(Exception exception, WebRequest webRequest) {
        final String ERROR_MESSAGE = "An unexpected error occurred";
        logger.error(ERROR_MESSAGE, exception);
        ApiErrorResponse errorResponse = getErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ERROR_MESSAGE);
        return handleExceptionInternal(exception, errorResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR,
                webRequest);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Object> handleUserAlreadyExistsException(UserAlreadyExistsException exception, WebRequest webRequest)
    {
        final String ERROR_MESSAGE = exception.getMessage();
        logger.error(ERROR_MESSAGE, exception);
        ApiErrorResponse errorResponse = getErrorResponse(HttpStatus.BAD_REQUEST, ERROR_MESSAGE);
        return  handleExceptionInternal(exception, errorResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, webRequest);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException exception, WebRequest webRequest)
    {
        final String ERROR_MESSAGE = exception.getMessage();
        logger.error(ERROR_MESSAGE, exception);
        ApiErrorResponse errorResponse = getErrorResponse(HttpStatus.NOT_FOUND, ERROR_MESSAGE);
        return  handleExceptionInternal(exception, errorResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, webRequest);
    }

    @ExceptionHandler(BirthDateValidationException.class)
    public ResponseEntity<Object> handleBirthDateValidation(BirthDateValidationException exception, WebRequest webRequest)
    {
        final String ERROR_MESSAGE = exception.getMessage();
        logger.error(ERROR_MESSAGE, exception);
        ApiErrorResponse errorResponse = getErrorResponse(HttpStatus.BAD_REQUEST, ERROR_MESSAGE);
        return  handleExceptionInternal(exception, errorResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, webRequest);
    }

    private ApiErrorResponse getErrorResponse(HttpStatus status, String errorMessage) {
        if (StringUtils.isEmpty(errorMessage)) {
            errorMessage = "An unexpected error occurred";
        }
        return new ApiErrorResponse(Instant.now().toString(),status.value(), status.name(),  errorMessage);
    }
}
