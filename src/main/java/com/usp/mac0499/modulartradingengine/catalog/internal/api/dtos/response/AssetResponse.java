package com.usp.mac0499.modulartradingengine.catalog.internal.api.dtos.response;

import com.usp.mac0499.modulartradingengine.sharedkernel.api.dtos.response.MoneyResponse;

import java.util.UUID;

public record AssetResponse(UUID id, String code, MoneyResponse price, Integer quantity) {
}
