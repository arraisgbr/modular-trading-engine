package com.usp.mac0499.trading.api.dtos.request;


import com.usp.mac0499.sharedkernel.api.dtos.request.MoneyRequest;
import com.usp.mac0499.sharedkernel.domain.values.OrderType;

import java.util.UUID;

public record OrderRequest(UUID portfolioId, UUID assetId, Long quantity, MoneyRequest price, OrderType type) {
}
