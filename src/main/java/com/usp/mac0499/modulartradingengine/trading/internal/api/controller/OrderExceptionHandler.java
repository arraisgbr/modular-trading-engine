package com.usp.mac0499.modulartradingengine.trading.internal.api.controller;

import com.usp.mac0499.modulartradingengine.trading.internal.api.dtos.response.ErrorResponse;
import com.usp.mac0499.modulartradingengine.trading.internal.domain.exceptions.OrderCantBeCancelledException;
import com.usp.mac0499.modulartradingengine.trading.internal.domain.exceptions.OrderNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice(basePackageClasses = OrderController.class)
public class OrderExceptionHandler {

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleOrderNotFound(OrderNotFoundException ex, HttpServletRequest request) {
        var errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase(), ex.getMessage(), request.getRequestURI(), Instant.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OrderCantBeCancelledException.class)
    public ResponseEntity<ErrorResponse> handleInsufficientBalance(OrderCantBeCancelledException ex, HttpServletRequest request) {
        var errorResponse = new ErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY.value(), HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase(), ex.getMessage(), request.getRequestURI(), Instant.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
    }

}
