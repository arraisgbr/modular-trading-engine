package com.usp.mac0499.sharedkernel.events;

import com.usp.mac0499.sharedkernel.domain.values.Money;
import org.springframework.modulith.events.Externalized;

import java.util.UUID;

@Externalized(target = MessageBrokerConstants.BUY_ORDER_CREATED_EXCHANGE)
public record BuyOrderCreated(UUID portfolioId, Money price, Long quantity) {
}