package com.usp.mac0499.modulartradingengine.trading.external;

import java.util.UUID;

public record SellOrderCreated(UUID portfolioId, UUID assetId, Long quantity) {
}