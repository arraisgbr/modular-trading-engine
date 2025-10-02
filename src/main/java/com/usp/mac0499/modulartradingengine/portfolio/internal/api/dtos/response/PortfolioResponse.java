package com.usp.mac0499.modulartradingengine.portfolio.internal.api.dtos.response;

import java.math.BigDecimal;
import java.util.UUID;

public record PortfolioResponse(UUID id, BigDecimal availableBalance, BigDecimal reservedBalance) {
}