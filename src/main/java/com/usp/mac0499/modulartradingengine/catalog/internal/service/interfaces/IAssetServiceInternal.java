package com.usp.mac0499.modulartradingengine.catalog.internal.service.interfaces;

import com.usp.mac0499.modulartradingengine.catalog.internal.domain.entities.Asset;

import java.util.List;
import java.util.UUID;

public interface IAssetServiceInternal {

    Asset createAsset(Asset asset);

    Asset readAsset(UUID id);

    List<Asset> readAssets();

    void deleteAsset(UUID id);

}
