package com.usp.mac0499.sharedkernel.events;

import org.springframework.modulith.events.Externalized;

import java.util.UUID;

@Externalized(target = MessageBrokerConstants.PORTFOLIO_DELETED_EXCHANGE)
public record PortfolioDeleted(UUID portfolioId) {
}