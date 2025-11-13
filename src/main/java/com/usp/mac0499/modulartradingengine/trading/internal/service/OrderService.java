package com.usp.mac0499.modulartradingengine.trading.internal.service;

import com.usp.mac0499.modulartradingengine.trading.external.IOrderServiceExternal;
import com.usp.mac0499.modulartradingengine.trading.internal.domain.entities.Order;
import com.usp.mac0499.modulartradingengine.trading.internal.domain.exceptions.OrderNotFoundException;
import com.usp.mac0499.modulartradingengine.trading.internal.infrastructure.repositories.OrderRepository;
import com.usp.mac0499.modulartradingengine.trading.internal.service.interfaces.IOrderServiceInternal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderServiceInternal, IOrderServiceExternal {

    private final OrderRepository orderRepository;

    @Override
    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Order readOrder(UUID id) {
        return orderRepository.findValidOrderById(id).orElseThrow(() -> new OrderNotFoundException(id));
    }

    @Override
    public List<Order> readOrders() {
        return orderRepository.findValidOrders();
    }

    @Override
    public void cancelOrder(UUID id) {
        orderRepository.findById(id).ifPresentOrElse(order -> {
            order.cancelOrder();
            orderRepository.save(order);
        }, () -> {
            throw new OrderNotFoundException(id);
        });
    }

}
