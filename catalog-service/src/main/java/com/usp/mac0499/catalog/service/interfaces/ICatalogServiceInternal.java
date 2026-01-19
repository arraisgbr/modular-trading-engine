package com.usp.mac0499.catalog.service.interfaces;


import com.usp.mac0499.catalog.domain.entities.Asset;

import java.util.List;
import java.util.UUID;

public interface ICatalogServiceInternal {

    Asset createAsset(Asset asset);

    Asset readAsset(UUID id);

    List<Asset> readAssets();

    void deleteAsset(UUID id);

}
