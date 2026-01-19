package com.usp.mac0499.trading.service;

import com.usp.mac0499.sharedkernel.events.AssetDeleted;
import com.usp.mac0499.sharedkernel.events.PortfolioDeleted;
import com.usp.mac0499.trading.infrastructure.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TradingListener {

    private final OrderRepository orderRepository;

    @RabbitListener(queues = "trading.removeOrdersInvolvingAsset.queue")
    public void removeOrdersInvolvingAsset(AssetDeleted event) {
        orderRepository.findValidOrdersByAssetId(event.assetId()).forEach(order -> {
            order.cancelOrder();
            orderRepository.save(order);
        });
    }

    @RabbitListener(queues = "trading.removeOrdersInvolvingPortfolio.queue")
    public void removeOrdersInvolvingPortfolio(PortfolioDeleted event) {
        orderRepository.findValidOrdersByPortfolioId(event.portfolioId()).forEach(order -> {
            order.cancelOrder();
            orderRepository.save(order);
        });
    }

}
