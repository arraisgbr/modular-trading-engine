package com.usp.mac0499.modulartradingengine.catalog.internal.service;

import com.usp.mac0499.modulartradingengine.catalog.external.IAssetServiceExternal;
import com.usp.mac0499.modulartradingengine.catalog.internal.infrastructure.repositories.AssetRepository;
import com.usp.mac0499.modulartradingengine.sharedkernel.events.TransactionCompleted;
import lombok.RequiredArgsConstructor;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AssetServiceExternal implements IAssetServiceExternal {

    private final AssetRepository assetRepository;

    @Override
    @ApplicationModuleListener
    public void updateAsset(TransactionCompleted event) {
        assetRepository.findById(event.assetId()).ifPresent(asset -> {
            asset.updatePrice(event.price());
            assetRepository.save(asset);
        });
    }

}
