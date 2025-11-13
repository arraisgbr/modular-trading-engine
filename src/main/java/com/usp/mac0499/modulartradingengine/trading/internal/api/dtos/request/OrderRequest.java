package com.usp.mac0499.modulartradingengine.trading.internal.api.dtos.request;

import com.usp.mac0499.modulartradingengine.sharedkernel.api.dtos.request.MoneyRequest;
import com.usp.mac0499.modulartradingengine.sharedkernel.domain.values.OrderType;

import java.util.UUID;

public record OrderRequest(UUID portfolioId, UUID assetId, Long quantity, MoneyRequest price, OrderType type) {
}
