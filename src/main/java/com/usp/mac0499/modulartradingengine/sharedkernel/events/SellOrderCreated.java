package com.usp.mac0499.modulartradingengine.sharedkernel.events;

import java.util.UUID;

public record SellOrderCreated(UUID portfolioId, UUID assetId, Long quantity) {
}