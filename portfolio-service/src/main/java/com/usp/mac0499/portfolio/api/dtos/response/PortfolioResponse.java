package com.usp.mac0499.portfolio.api.dtos.response;


import com.usp.mac0499.sharedkernel.api.dtos.response.MoneyResponse;

import java.util.UUID;

public record PortfolioResponse(UUID id, MoneyResponse availableBalance, MoneyResponse reservedBalance) {
}