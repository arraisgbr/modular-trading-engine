package com.usp.mac0499.modulartradingengine.catalog.internal.service;

import com.usp.mac0499.modulartradingengine.catalog.internal.domain.entities.Asset;
import com.usp.mac0499.modulartradingengine.catalog.internal.domain.exceptions.AssetNotFoundException;
import com.usp.mac0499.modulartradingengine.catalog.internal.infrastructure.repositories.AssetRepository;
import com.usp.mac0499.modulartradingengine.catalog.internal.service.interfaces.IAssetServiceInternal;
import com.usp.mac0499.modulartradingengine.portfolio.external.IPortfolioServiceExternal;
import com.usp.mac0499.modulartradingengine.trading.external.IOrderServiceExternal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AssetServiceInternal implements IAssetServiceInternal {

    private final AssetRepository assetRepository;
    private final IOrderServiceExternal orderService;
    private final IPortfolioServiceExternal portfolioService;

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
        assetRepository.findById(id).ifPresentOrElse(asset -> {
            orderService.removeOrdersInvolvingAsset(asset.getId());
            portfolioService.removeDisabledAssetFromPortfolio(asset.getId(), asset.getPrice());
            assetRepository.delete(asset);
        }, () -> {
            throw new AssetNotFoundException(id);
        });
    }

}
