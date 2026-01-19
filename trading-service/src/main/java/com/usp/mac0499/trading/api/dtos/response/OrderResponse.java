package com.usp.mac0499.trading.api.dtos.response;

import com.usp.mac0499.sharedkernel.api.dtos.response.MoneyResponse;
import com.usp.mac0499.sharedkernel.domain.values.OrderStatus;
import com.usp.mac0499.sharedkernel.domain.values.OrderType;

import java.util.UUID;

public record OrderResponse(UUID id, UUID portfolioId, UUID assetId, Long quantity, MoneyResponse price,
                            OrderType orderType, OrderStatus orderStatus) {
}
