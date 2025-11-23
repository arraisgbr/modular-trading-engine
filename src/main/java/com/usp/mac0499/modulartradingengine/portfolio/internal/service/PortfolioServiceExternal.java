package com.usp.mac0499.modulartradingengine.portfolio.internal.service;

import com.usp.mac0499.modulartradingengine.portfolio.external.IPortfolioServiceExternal;
import com.usp.mac0499.modulartradingengine.portfolio.internal.domain.entities.Portfolio;
import com.usp.mac0499.modulartradingengine.portfolio.internal.domain.exceptions.PortfolioNotFoundException;
import com.usp.mac0499.modulartradingengine.portfolio.internal.infrastructure.repositories.PortfolioRepository;
import com.usp.mac0499.modulartradingengine.sharedkernel.domain.values.Money;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PortfolioServiceExternal implements IPortfolioServiceExternal {

    private final PortfolioRepository portfolioRepository;

    @Override
    @Transactional
    public void executeTransaction(UUID debtorId, UUID creditorId, UUID assetId, Long assetQuantity, Money price) {
        Portfolio debtorPortfolio = portfolioRepository.findById(debtorId).orElseThrow(() -> new PortfolioNotFoundException(debtorId));
        Portfolio creditorPortfolio = portfolioRepository.findById(creditorId).orElseThrow(() -> new PortfolioNotFoundException(creditorId));

        debtorPortfolio.executeDebtorTransaction(price, assetId, assetQuantity);
        creditorPortfolio.executeCreditorTransaction(price, assetId, assetQuantity);

        portfolioRepository.save(debtorPortfolio);
        portfolioRepository.save(creditorPortfolio);
    }

    @Override
    public void releaseBalance(UUID portfolioId, Money amount) {
        portfolioRepository.findById(portfolioId).ifPresentOrElse(portfolio -> {
            portfolio.releaseBalance(amount);
            portfolioRepository.save(portfolio);
        }, () -> {
            throw new PortfolioNotFoundException(portfolioId);
        });
    }

    @Override
    public void releaseAsset(UUID portfolioId, UUID assetId, Long quantity) {
        portfolioRepository.findByAssets_AssetId(assetId).forEach(portfolio -> {
            portfolio.releaseAsset(assetId, quantity);
            portfolioRepository.save(portfolio);
        });
    }

    @Override
    public void reserveBalance(UUID portfolioId, Money amount) {
        portfolioRepository.findById(portfolioId).ifPresentOrElse(portfolio -> {
            portfolio.reserveBalance(amount);
            portfolioRepository.save(portfolio);
        }, () -> {
            throw new PortfolioNotFoundException(portfolioId);
        });
    }

    @Override
    public void reserveAsset(UUID portfolioId, UUID assetId, Long quantity) {
        portfolioRepository.findByAssets_AssetId(assetId).forEach(portfolio -> {
            portfolio.reserveAsset(assetId, quantity);
            portfolioRepository.save(portfolio);
        });
    }

    @Override
    @Transactional
    public void removeDisabledAssetFromPortfolio(UUID assetId, Money price) {
        List<Portfolio> portfoliosToUpdate = portfolioRepository.findByAssets_AssetId(assetId);
        for (Portfolio portfolio : portfoliosToUpdate) {
            portfolio.removeAsset(assetId, price);
            portfolioRepository.save(portfolio);
        }
    }

}
