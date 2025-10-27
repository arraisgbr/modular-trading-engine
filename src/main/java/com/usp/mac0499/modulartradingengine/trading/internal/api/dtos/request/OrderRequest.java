package com.usp.mac0499.modulartradingengine.trading.internal.api.dtos.request;

import com.usp.mac0499.modulartradingengine.trading.internal.domain.enums.OrderType;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderRequest(UUID portfolioId, UUID assetId, Long quantity, BigDecimal price, OrderType type) {
}
