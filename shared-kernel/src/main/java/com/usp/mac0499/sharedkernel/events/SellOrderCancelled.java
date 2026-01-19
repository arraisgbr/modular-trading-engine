package com.usp.mac0499.sharedkernel.events;

import org.springframework.modulith.events.Externalized;

import java.util.UUID;

@Externalized(target = MessageBrokerConstants.SELL_ORDER_CANCELLED_EXCHANGE)
public record SellOrderCancelled(UUID portfolioId, UUID assetId, Long quantity) {
}