package com.usp.mac0499.modulartradingengine.trading.internal.service;

import com.usp.mac0499.modulartradingengine.catalog.external.AssetDeleted;
import com.usp.mac0499.modulartradingengine.portfolio.external.PortfolioDeleted;
import com.usp.mac0499.modulartradingengine.trading.internal.infrastructure.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderListener {

    private final OrderRepository orderRepository;

    @ApplicationModuleListener
    public void removeOrdersInvolvingAsset(AssetDeleted event) {
        orderRepository.findValidOrdersByAssetId(event.assetId()).forEach(order -> {
            order.cancelOrder();
            orderRepository.save(order);
        });
    }

    @ApplicationModuleListener
    public void removeOrdersInvolvingPortfolio(PortfolioDeleted event) {
        orderRepository.findValidOrdersByPortfolioId(event.portfolioId()).forEach(order -> {
            order.cancelOrder();
            orderRepository.save(order);
        });
    }

}
