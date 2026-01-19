package com.usp.mac0499.sharedkernel.events;

import com.usp.mac0499.sharedkernel.domain.values.Money;
import org.springframework.modulith.events.Externalized;

import java.util.UUID;

@Externalized(target = MessageBrokerConstants.ASSET_DELETED_EXCHANGE)
public record AssetDeleted(UUID assetId, Money price) {
}
