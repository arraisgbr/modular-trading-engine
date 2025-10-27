package com.usp.mac0499.modulartradingengine.trading.internal.api.mappers;

import com.usp.mac0499.modulartradingengine.trading.internal.api.dtos.response.OrderResponse;
import com.usp.mac0499.modulartradingengine.trading.internal.domain.entities.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    public OrderResponse toResponse(Order order) {
        return new OrderResponse(order.getId(), order.getPortfolioId(), order.getAssetId(), order.getQuantity(), order.getPrice().value(), order.getType(), order.getStatus());
    }

}
