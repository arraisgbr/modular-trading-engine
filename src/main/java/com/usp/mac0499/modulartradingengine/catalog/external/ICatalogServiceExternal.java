package com.usp.mac0499.modulartradingengine.catalog.external;

import com.usp.mac0499.modulartradingengine.sharedkernel.domain.values.Money;

import java.util.UUID;

public interface ICatalogServiceExternal {

    void updateAsset(UUID assetId, Money updatedPrice);

}