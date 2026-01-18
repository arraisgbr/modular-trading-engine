package com.usp.mac0499.modulartradingengine.portfolio.internal.service;

import com.usp.mac0499.modulartradingengine.portfolio.internal.domain.entities.Portfolio;
import com.usp.mac0499.modulartradingengine.portfolio.internal.domain.exceptions.PortfolioNotFoundException;
import com.usp.mac0499.modulartradingengine.portfolio.internal.infrastructure.repositories.PortfolioRepository;
import com.usp.mac0499.modulartradingengine.sharedkernel.domain.values.Money;
import com.usp.mac0499.modulartradingengine.sharedkernel.events.*;
import lombok.RequiredArgsConstructor;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PortfolioListener {

    private final PortfolioRepository portfolioRepository;

    @ApplicationModuleListener
    public void executeTransaction(TransactionCompleted event) {
        Portfolio debtorPortfolio = portfolioRepository.findById(event.debtorPortfolioId()).orElseThrow(() -> new PortfolioNotFoundException(event.debtorPortfolioId()));
        Portfolio creditorPortfolio = portfolioRepository.findById(event.creditorPortfolioId()).orElseThrow(() -> new PortfolioNotFoundException(event.creditorPortfolioId()));

        debtorPortfolio.executeDebtorTransaction(event.price(), event.assetId(), event.quantity());
        creditorPortfolio.executeCreditorTransaction(event.price(), event.assetId(), event.quantity());

        portfolioRepository.save(debtorPortfolio);
        portfolioRepository.save(creditorPortfolio);
    }

    @ApplicationModuleListener
    public void releaseBalance(BuyOrderCancelled event) {
        portfolioRepository.findById(event.portfolioId()).ifPresentOrElse(portfolio -> {
            Money amount = event.price().multiply(new Money(BigDecimal.valueOf(event.quantity())));
            portfolio.releaseBalance(amount);
            portfolioRepository.save(portfolio);
        }, () -> {
            throw new PortfolioNotFoundException(event.portfolioId());
        });
    }

    @ApplicationModuleListener
    public void releaseAsset(SellOrderCancelled event) {
        portfolioRepository.findByAssets_AssetId(event.assetId()).forEach(portfolio -> {
            portfolio.releaseAsset(event.assetId(), event.quantity());
            portfolioRepository.save(portfolio);
        });
    }

    @ApplicationModuleListener
    public void reserveBalance(BuyOrderCreated event) {
        portfolioRepository.findById(event.portfolioId()).ifPresentOrElse(portfolio -> {
            Money amount = event.price().multiply(new Money(BigDecimal.valueOf(event.quantity())));
            portfolio.reserveBalance(amount);
            portfolioRepository.save(portfolio);
        }, () -> {
            throw new PortfolioNotFoundException(event.portfolioId());
        });
    }

    @ApplicationModuleListener
    public void reserveAsset(SellOrderCreated event) {
        portfolioRepository.findByAssets_AssetId(event.assetId()).forEach(portfolio -> {
            portfolio.reserveAsset(event.assetId(), event.quantity());
            portfolioRepository.save(portfolio);
        });
    }

    @ApplicationModuleListener
    public void removeDisabledAssetFromPortfolio(AssetDeleted event) {
        List<Portfolio> portfoliosToUpdate = portfolioRepository.findByAssets_AssetId(event.assetId());
        for (Portfolio portfolio : portfoliosToUpdate) {
            portfolio.removeAsset(event.assetId(), event.price());
            portfolioRepository.save(portfolio);
        }
    }

}
