package com.usp.mac0499.modulartradingengine.catalog.internal.api.dtos.response;

import java.math.BigDecimal;
import java.util.UUID;

public record AssetResponse(UUID id, String code, BigDecimal price, Integer quantity) {
}
