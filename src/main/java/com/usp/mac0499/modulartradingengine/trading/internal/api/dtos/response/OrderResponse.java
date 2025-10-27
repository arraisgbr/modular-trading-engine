package com.usp.mac0499.modulartradingengine.trading.internal.api.dtos.response;

import com.usp.mac0499.modulartradingengine.trading.internal.domain.enums.OrderStatus;
import com.usp.mac0499.modulartradingengine.trading.internal.domain.enums.OrderType;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderResponse(UUID id, UUID portfolioId, UUID assetId, Long quantity, BigDecimal price,
                            OrderType orderType, OrderStatus orderStatus) {
}
