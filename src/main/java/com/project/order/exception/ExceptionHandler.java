package com.project.order.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleInvalidInput(MethodArgumentNotValidException exception) {
        Map<String, String> map = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(err -> {
            map.put(err.getField(), err.getDefaultMessage());
        });
        return map;
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(OrderNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleOrderNotFoundException(OrderNotFoundException exception) {
        Map<String, String> map = new HashMap<>();
        map.put("error", exception.getMessage());
        return map;
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(InvalidDataException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleInvalidDataException(InvalidDataException exception) {
        Map<String, String> map = new HashMap<>();
        map.put("error", exception.getMessage());
        return map;
    }


    @org.springframework.web.bind.annotation.ExceptionHandler(OrderNotTakenException.class)
    @ResponseStatus(HttpStatus.NOT_MODIFIED)
    public Map<String, String> handleOrderNotTakenException(OrderNotTakenException exception) {
        Map<String, String> map = new HashMap<>();
        map.put("error", exception.getMessage());
        return map;
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(OrderPresentException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> handleOrderPresentException(OrderPresentException exception) {
        Map<String, String> map = new HashMap<>();
        map.put("error", exception.getMessage());
        return map;
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(ApiResponseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleApiResponseException(ApiResponseException exception) {
        Map<String, String> map = new HashMap<>();
        map.put("error", exception.getMessage());
        return map;
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(UnKnownException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> handleApiResponseException(UnKnownException exception) {
        Map<String, String> map = new HashMap<>();
        map.put("error", exception.getMessage());
        return map;
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleApiResponseException(Exception exception) {
        Map<String, String> map = new HashMap<>();
        map.put("error", exception.getMessage());
        return map;
    }
}
