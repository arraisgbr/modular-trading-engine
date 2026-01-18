package com.usp.mac0499.modulartradingengine.catalog.internal.service;

import com.usp.mac0499.modulartradingengine.catalog.internal.infrastructure.repositories.AssetRepository;
import com.usp.mac0499.modulartradingengine.trading.external.TransactionCompleted;
import lombok.RequiredArgsConstructor;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AssetListener {

    private final AssetRepository assetRepository;

    @ApplicationModuleListener
    public void updateAsset(TransactionCompleted event) {
        assetRepository.findById(event.assetId()).ifPresent(asset -> {
            asset.updatePrice(event.price());
            assetRepository.save(asset);
        });
    }

}
