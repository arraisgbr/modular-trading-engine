package com.usp.mac0499.modulartradingengine.catalog.internal.api.mappers;

import com.usp.mac0499.modulartradingengine.catalog.internal.api.dtos.request.AssetRequest;
import com.usp.mac0499.modulartradingengine.catalog.internal.api.dtos.response.AssetResponse;
import com.usp.mac0499.modulartradingengine.catalog.internal.domain.entities.Asset;
import com.usp.mac0499.modulartradingengine.sharedkernel.api.mappers.MoneyMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AssetMapper {

    private final MoneyMapper moneyMapper;

    public AssetResponse toResponse(Asset asset) {
        return new AssetResponse(asset.getId(), asset.getCode(), moneyMapper.toDto(asset.getPrice()), asset.getQuantity());
    }

    public Asset toEntity(AssetRequest assetRequest) {
        return new Asset(assetRequest.code(), moneyMapper.toEntity(assetRequest.price()), assetRequest.quantity());
    }

}
