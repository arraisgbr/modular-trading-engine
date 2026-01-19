package com.usp.mac0499.trading.api.mappers;

import com.usp.mac0499.trading.api.dtos.request.OrderRequest;
import com.usp.mac0499.trading.api.dtos.response.OrderResponse;
import com.usp.mac0499.trading.domain.entities.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderMapper {

    private final MoneyMapper moneyMapper;

    public OrderResponse toResponse(Order order) {
        return new OrderResponse(order.getId(), order.getPortfolioId(), order.getAssetId(), order.getQuantity(), moneyMapper.toDto(order.getPrice()), order.getType(), order.getStatus());
    }

    public Order toEntity(OrderRequest orderRequest) {
        return new Order(orderRequest.portfolioId(), orderRequest.assetId(), orderRequest.quantity(), moneyMapper.toEntity(orderRequest.price()), orderRequest.type());
    }

}
