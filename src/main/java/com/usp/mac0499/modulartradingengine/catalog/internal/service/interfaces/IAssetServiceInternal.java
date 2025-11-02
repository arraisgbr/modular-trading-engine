package com.usp.mac0499.modulartradingengine.catalog.internal.service.interfaces;

import com.usp.mac0499.modulartradingengine.catalog.internal.domain.entities.Asset;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface IAssetServiceInternal {

    Asset createAsset(String code, BigDecimal price, Integer quantity);

    Asset readAsset(UUID id);

    List<Asset> readAssets();

    void deleteAsset(UUID id);

}
