package com.usp.mac0499.trading.domain.exceptions;

import java.util.UUID;

public class OrderCantBeCancelledException extends RuntimeException {

    public OrderCantBeCancelledException(UUID id) {
        super("Order can't be cancelled with id: " + id);
    }
}
