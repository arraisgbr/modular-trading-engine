package com.usp.mac0499.modulartradingengine.trading.internal.service;

import com.usp.mac0499.modulartradingengine.sharedkernel.events.AssetDeleted;
import com.usp.mac0499.modulartradingengine.trading.external.IOrderServiceExternal;
import com.usp.mac0499.modulartradingengine.trading.internal.infrastructure.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceExternal implements IOrderServiceExternal {

    private final OrderRepository orderRepository;

    @Override
    @ApplicationModuleListener
    public void removeOrdersInvolvingAsset(AssetDeleted event) {
        orderRepository.findValidOrdersByAssetId(event.assetId()).forEach(order -> {
            order.cancelOrder();
            orderRepository.save(order);
        });
    }

    @Override
    public void removeOrdersInvolvingPortfolio(UUID portfolioId) {
        orderRepository.findValidOrdersByPortfolioId(portfolioId).forEach(order -> {
            order.cancelOrder();
            orderRepository.save(order);
        });
    }

}
