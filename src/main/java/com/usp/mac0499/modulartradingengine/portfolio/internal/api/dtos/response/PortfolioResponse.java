package com.usp.mac0499.modulartradingengine.portfolio.internal.api.dtos.response;

import com.usp.mac0499.modulartradingengine.sharedkernel.api.dtos.response.MoneyResponse;

import java.util.UUID;

public record PortfolioResponse(UUID id, MoneyResponse availableBalance, MoneyResponse reservedBalance) {
}