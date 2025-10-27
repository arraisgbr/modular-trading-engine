package com.usp.mac0499.modulartradingengine.catalog.internal.api.dtos.request;

import java.math.BigDecimal;

public record AssetRequest(String code, BigDecimal value, Integer quantity) {
}
