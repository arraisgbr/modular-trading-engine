package com.usp.mac0499.catalog.api.mappers;

import com.usp.mac0499.catalog.api.dtos.request.AssetRequest;
import com.usp.mac0499.catalog.api.dtos.response.AssetResponse;
import com.usp.mac0499.catalog.domain.entities.Asset;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AssetMapper {

    private final MoneyMapper moneyMapper;

    public AssetResponse toResponse(Asset asset) {
        return new AssetResponse(asset.getId(), asset.getCode(), moneyMapper.toDto(asset.getPrice()));
    }

    public Asset toEntity(AssetRequest assetRequest) {
        return new Asset(assetRequest.code(), moneyMapper.toEntity(assetRequest.price()));
    }

}
