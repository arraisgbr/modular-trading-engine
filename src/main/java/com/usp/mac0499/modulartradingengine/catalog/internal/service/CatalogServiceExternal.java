package com.usp.mac0499.modulartradingengine.catalog.internal.service;

import com.usp.mac0499.modulartradingengine.catalog.external.ICatalogServiceExternal;
import com.usp.mac0499.modulartradingengine.catalog.internal.infrastructure.repositories.AssetRepository;
import com.usp.mac0499.modulartradingengine.sharedkernel.domain.values.Money;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CatalogServiceExternal implements ICatalogServiceExternal {

    private final AssetRepository assetRepository;

    @Override
    public void updateAsset(UUID assetId, Money salePrice) {
        assetRepository.findById(assetId).ifPresent(asset -> {
            asset.updatePrice(salePrice);
            assetRepository.save(asset);
        });
    }

}
