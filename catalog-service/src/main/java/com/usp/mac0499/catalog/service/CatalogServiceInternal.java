package com.usp.mac0499.catalog.service;

import com.usp.mac0499.catalog.domain.entities.Asset;
import com.usp.mac0499.catalog.domain.exceptions.AssetNotFoundException;
import com.usp.mac0499.catalog.infrastructure.repositories.AssetRepository;
import com.usp.mac0499.catalog.service.interfaces.ICatalogServiceInternal;
import com.usp.mac0499.sharedkernel.events.AssetDeleted;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CatalogServiceInternal implements ICatalogServiceInternal {

    private final AssetRepository assetRepository;
    private final ApplicationEventPublisher events;

    @Override
    public Asset createAsset(Asset asset) {
        return assetRepository.save(asset);
    }

    @Override
    public Asset readAsset(UUID id) {
        return assetRepository.findById(id).orElseThrow(() -> new AssetNotFoundException(id));
    }

    @Override
    public List<Asset> readAssets() {
        return assetRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteAsset(UUID id) {
        assetRepository.findById(id).ifPresentOrElse(asset -> {
            events.publishEvent(new AssetDeleted(asset.getId(), asset.getPrice()));
            assetRepository.delete(asset);
        }, () -> {
            throw new AssetNotFoundException(id);
        });
    }

}
