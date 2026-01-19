package com.usp.mac0499.sharedkernel.events;

import org.springframework.modulith.events.Externalized;

import java.util.UUID;

@Externalized(target = MessageBrokerConstants.SELL_ORDER_CREATED_EXCHANGE)
public record SellOrderCreated(UUID portfolioId, UUID assetId, Long quantity) {
}