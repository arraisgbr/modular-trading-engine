package com.usp.mac0499.modulartradingengine.trading.internal.domain.exceptions;

import java.util.UUID;

public class OrderNotFoundException extends RuntimeException {

    public OrderNotFoundException(UUID id) { super("Order not found with id: " + id); }
    
}
