package com.usp.mac0499.modulartradingengine.trading.internal.service;

import com.usp.mac0499.modulartradingengine.trading.external.ITradingServiceExternal;
import com.usp.mac0499.modulartradingengine.trading.internal.infrastructure.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TradingServiceExternal implements ITradingServiceExternal {

    private final OrderRepository orderRepository;

    @Override
    public void removeOrdersInvolvingAsset(UUID assetId) {
        orderRepository.findValidOrdersByAssetId(assetId).forEach(order -> {
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
