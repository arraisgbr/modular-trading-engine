package com.usp.mac0499.trading.service;

import com.usp.mac0499.sharedkernel.domain.values.OrderType;
import com.usp.mac0499.sharedkernel.events.*;
import com.usp.mac0499.trading.domain.entities.Order;
import com.usp.mac0499.trading.domain.exceptions.OrderNotFoundException;
import com.usp.mac0499.trading.infrastructure.repositories.OrderRepository;
import com.usp.mac0499.trading.service.interfaces.ITradingServiceInternal;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TradingServiceInternal implements ITradingServiceInternal {

    private final OrderRepository orderRepository;
    private final ApplicationEventPublisher events;

    @Override
    @Transactional
    public Order createOrder(Order order) {
        if (order.getType() == OrderType.BUY) {
            events.publishEvent(new BuyOrderCreated(order.getPortfolioId(), order.getPrice(), order.getQuantity()));
        } else {
            events.publishEvent(new SellOrderCreated(order.getPortfolioId(), order.getAssetId(), order.getQuantity()));
        }
        return tryToMatchOrder(order);
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
    @Transactional
    public void cancelOrder(UUID id) {
        orderRepository.findById(id).ifPresentOrElse(order -> {
            order.cancelOrder();
            if (order.getType() == OrderType.BUY) {
                events.publishEvent(new BuyOrderCancelled(order.getPortfolioId(), order.getPrice(), order.getQuantity()));
            } else {
                events.publishEvent(new SellOrderCancelled(order.getPortfolioId(), order.getAssetId(), order.getQuantity()));
            }
            orderRepository.save(order);
        }, () -> {
            throw new OrderNotFoundException(id);
        });
    }

    private Order tryToMatchOrder(Order order) {
        orderRepository.findOrderByAssetIdAndValueAndType(order.getAssetId(), order.getPrice(), order.getType() == OrderType.BUY ? OrderType.SELL : OrderType.BUY).ifPresent(matchOrder -> {
            var sellOrder = order.getType() == OrderType.SELL ? order : matchOrder;
            var buyOrder = order.getType() == OrderType.BUY ? order : matchOrder;
            matchOrder.cancelOrder();
            order.cancelOrder();
            orderRepository.save(matchOrder);
            events.publishEvent(new TransactionCompleted(buyOrder.getPortfolioId(), sellOrder.getPortfolioId(), order.getAssetId(), order.getQuantity(), order.getPrice()));
        });
        return orderRepository.save(order);
    }
}
