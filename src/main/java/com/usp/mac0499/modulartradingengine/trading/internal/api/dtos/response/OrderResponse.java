package com.usp.mac0499.modulartradingengine.trading.internal.api.dtos.response;

import com.usp.mac0499.modulartradingengine.sharedkernel.api.dtos.response.MoneyResponse;
import com.usp.mac0499.modulartradingengine.sharedkernel.domain.values.OrderStatus;
import com.usp.mac0499.modulartradingengine.sharedkernel.domain.values.OrderType;

import java.util.UUID;

public record OrderResponse(UUID id, UUID portfolioId, UUID assetId, Long quantity, MoneyResponse price,
                            OrderType orderType, OrderStatus orderStatus) {
}
