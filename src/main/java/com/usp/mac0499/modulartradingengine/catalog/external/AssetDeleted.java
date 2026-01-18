package com.usp.mac0499.modulartradingengine.catalog.external;

import com.usp.mac0499.modulartradingengine.sharedkernel.domain.values.Money;

import java.util.UUID;

public record AssetDeleted(UUID assetId, Money price) {
}
