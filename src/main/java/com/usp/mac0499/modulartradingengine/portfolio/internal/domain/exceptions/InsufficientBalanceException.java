package com.usp.mac0499.modulartradingengine.portfolio.internal.domain.exceptions;

public class InsufficientBalanceException extends RuntimeException {

    public InsufficientBalanceException(String message) {
        super(message);
    }

}