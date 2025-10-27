package com.usp.mac0499.modulartradingengine.catalog.internal.api.mappers;

import com.usp.mac0499.modulartradingengine.catalog.internal.api.dtos.response.AssetResponse;
import com.usp.mac0499.modulartradingengine.catalog.internal.domain.entities.Asset;
import org.springframework.stereotype.Component;

@Component
public class AssetMapper {

    public AssetResponse toResponse(Asset asset) {
        return new AssetResponse(
                asset.getId(),
                asset.getCode(),
                asset.getPrice().value(),
                asset.getQuantity()
        );
    }

}
