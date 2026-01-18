package com.usp.mac0499.modulartradingengine.trading.internal.service;

import com.usp.mac0499.modulartradingengine.catalog.external.ICatalogServiceExternal;
import com.usp.mac0499.modulartradingengine.portfolio.external.IPortfolioServiceExternal;
import com.usp.mac0499.modulartradingengine.sharedkernel.domain.values.Money;
import com.usp.mac0499.modulartradingengine.sharedkernel.domain.values.OrderType;
import com.usp.mac0499.modulartradingengine.trading.internal.domain.entities.Order;
import com.usp.mac0499.modulartradingengine.trading.internal.domain.exceptions.OrderNotFoundException;
import com.usp.mac0499.modulartradingengine.trading.internal.infrastructure.repositories.OrderRepository;
import com.usp.mac0499.modulartradingengine.trading.internal.service.interfaces.ITradingServiceInternal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TradingServiceInternal implements ITradingServiceInternal {

    private final OrderRepository orderRepository;
    private final IPortfolioServiceExternal portfolioService;
    private final ICatalogServiceExternal assetService;

    @Override
    public Order createOrder(Order order) {
        if (order.getType() == OrderType.BUY) {
            portfolioService.reserveBalance(order.getPortfolioId(), order.getPrice().multiply(new Money(BigDecimal.valueOf(order.getQuantity()))));
        } else {
            portfolioService.reserveAsset(order.getPortfolioId(), order.getAssetId(), order.getQuantity());
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
    public void cancelOrder(UUID id) {
        orderRepository.findById(id).ifPresentOrElse(order -> {
            order.cancelOrder();
            if (order.getType() == OrderType.BUY) {
                portfolioService.releaseBalance(order.getPortfolioId(), order.getPrice().multiply(new Money(BigDecimal.valueOf(order.getQuantity()))));
            } else {
                portfolioService.releaseAsset(order.getPortfolioId(), order.getAssetId(), order.getQuantity());
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
            portfolioService.executeTransaction(buyOrder.getPortfolioId(), sellOrder.getPortfolioId(), order.getAssetId(), order.getQuantity(), order.getPrice());
            assetService.updateAsset(order.getAssetId(), order.getPrice());
        });
        return orderRepository.save(order);
    }
}
