package com.usp.mac0499.modulartradingengine.trading.external;

import java.util.UUID;

public record SellOrderCancelled(UUID portfolioId, UUID assetId, Long quantity) {
}