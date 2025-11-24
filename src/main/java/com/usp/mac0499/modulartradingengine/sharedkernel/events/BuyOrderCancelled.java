package com.usp.mac0499.modulartradingengine.sharedkernel.events;

import com.usp.mac0499.modulartradingengine.sharedkernel.domain.values.Money;

import java.util.UUID;

public record BuyOrderCancelled(UUID portfolioId, Money price, Long quantity) {
}