package com.usp.mac0499.modulartradingengine.catalog.internal.service;

import com.usp.mac0499.modulartradingengine.catalog.external.IAssetServiceExternal;
import com.usp.mac0499.modulartradingengine.catalog.internal.domain.entities.Asset;
import com.usp.mac0499.modulartradingengine.catalog.internal.domain.exceptions.AssetNotFoundException;
import com.usp.mac0499.modulartradingengine.catalog.internal.infrastructure.repositories.AssetRepository;
import com.usp.mac0499.modulartradingengine.catalog.internal.service.interfaces.IAssetServiceInternal;
import com.usp.mac0499.modulartradingengine.sharedkernel.domain.values.Money;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class AssetService implements IAssetServiceInternal, IAssetServiceExternal {

    private final AssetRepository assetRepository;

    public AssetService(AssetRepository assetRepository) {
        this.assetRepository = assetRepository;
    }

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
    public void deleteAsset(UUID id) {
        assetRepository.findById(id).ifPresentOrElse(assetRepository::delete, () -> {
            throw new AssetNotFoundException(id);
        });
    }

    @Override
    public void increaseQuantity(UUID id) {
        assetRepository.findById(id).ifPresentOrElse(asset -> {
            asset.increaseQuantity();
            assetRepository.save(asset);
        }, () -> {
            throw new AssetNotFoundException(id);
        });
    }

    @Override
    public void decreaseQuantity(UUID id) {
        assetRepository.findById(id).ifPresentOrElse(asset -> {
            asset.decreaseQuantity();
            assetRepository.save(asset);
        }, () -> {
            throw new AssetNotFoundException(id);
        });
    }

    @Override
    public BigDecimal getValue(UUID id) {
        var asset = assetRepository.findById(id).orElseThrow(() -> new AssetNotFoundException(id));
        return asset.getPrice().value();
    }

    @Override
    public void updateValue(UUID id, BigDecimal newPrice) {
        assetRepository.findById(id).ifPresentOrElse(asset -> {
            var price = new Money(newPrice);
            asset.setPrice(price);
            assetRepository.save(asset);
        }, () -> {
            throw new AssetNotFoundException(id);
        });
    }
}
