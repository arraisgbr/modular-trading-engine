package com.usp.mac0499.trading.service.interfaces;

import com.usp.mac0499.trading.domain.entities.Order;

import java.util.List;
import java.util.UUID;

public interface ITradingServiceInternal {

    Order createOrder(Order order);

    Order readOrder(UUID id);

    List<Order> readOrders();

    void cancelOrder(UUID id);

}
