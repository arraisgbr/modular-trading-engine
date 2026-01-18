package com.usp.mac0499.modulartradingengine.trading.external;

import com.usp.mac0499.modulartradingengine.sharedkernel.domain.values.Money;

import java.util.UUID;

public record BuyOrderCancelled(UUID portfolioId, Money price, Long quantity) {
}