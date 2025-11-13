package com.usp.mac0499.modulartradingengine.trading.internal.service.interfaces;

import com.usp.mac0499.modulartradingengine.trading.internal.domain.entities.Order;

import java.util.List;
import java.util.UUID;

public interface IOrderServiceInternal {

    Order createOrder(Order order);

    Order readOrder(UUID id);

    List<Order> readOrders();

    void cancelOrder(UUID id);

}
