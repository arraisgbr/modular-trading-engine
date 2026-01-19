package com.usp.mac0499.portfolio.service;

import com.usp.mac0499.portfolio.domain.entities.Portfolio;
import com.usp.mac0499.portfolio.domain.exceptions.PortfolioNotFoundException;
import com.usp.mac0499.portfolio.infrastructure.repositories.PortfolioRepository;
import com.usp.mac0499.sharedkernel.domain.values.Money;
import com.usp.mac0499.sharedkernel.events.*;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PortfolioListener {

    private final PortfolioRepository portfolioRepository;

    @RabbitListener(queues = "portfolio.transactionCompleted.queue")
    public void executeTransaction(TransactionCompleted event) {
        Portfolio debtorPortfolio = portfolioRepository.findById(event.debtorPortfolioId()).orElseThrow(() -> new PortfolioNotFoundException(event.debtorPortfolioId()));
        Portfolio creditorPortfolio = portfolioRepository.findById(event.creditorPortfolioId()).orElseThrow(() -> new PortfolioNotFoundException(event.creditorPortfolioId()));

        debtorPortfolio.executeDebtorTransaction(event.price(), event.assetId(), event.quantity());
        creditorPortfolio.executeCreditorTransaction(event.price(), event.assetId(), event.quantity());

        portfolioRepository.save(debtorPortfolio);
        portfolioRepository.save(creditorPortfolio);
    }

    @RabbitListener(queues = "portfolio.buyOrderCancelled.queue")
    public void releaseBalance(BuyOrderCancelled event) {
        portfolioRepository.findById(event.portfolioId()).ifPresentOrElse(portfolio -> {
            Money amount = event.price().multiply(new Money(BigDecimal.valueOf(event.quantity())));
            portfolio.releaseBalance(amount);
            portfolioRepository.save(portfolio);
        }, () -> {
            throw new PortfolioNotFoundException(event.portfolioId());
        });
    }

    @RabbitListener(queues = "portfolio.sellOrderCancelled.queue")
    public void releaseAsset(SellOrderCancelled event) {
        portfolioRepository.findByAssets_AssetId(event.assetId()).forEach(portfolio -> {
            portfolio.releaseAsset(event.assetId(), event.quantity());
            portfolioRepository.save(portfolio);
        });
    }

    @RabbitListener(queues = "portfolio.buyOrderCreated.queue")
    public void reserveBalance(BuyOrderCreated event) {
        portfolioRepository.findById(event.portfolioId()).ifPresentOrElse(portfolio -> {
            Money amount = event.price().multiply(new Money(BigDecimal.valueOf(event.quantity())));
            portfolio.reserveBalance(amount);
            portfolioRepository.save(portfolio);
        }, () -> {
            throw new PortfolioNotFoundException(event.portfolioId());
        });
    }

    @RabbitListener(queues = "portfolio.sellOrderCreated.queue")
    public void reserveAsset(SellOrderCreated event) {
        portfolioRepository.findByAssets_AssetId(event.assetId()).forEach(portfolio -> {
            portfolio.reserveAsset(event.assetId(), event.quantity());
            portfolioRepository.save(portfolio);
        });
    }

    @RabbitListener(queues = "portfolio.assetDeleted.queue")
    public void removeDisabledAssetFromPortfolio(AssetDeleted event) {
        List<Portfolio> portfoliosToUpdate = portfolioRepository.findByAssets_AssetId(event.assetId());
        for (Portfolio portfolio : portfoliosToUpdate) {
            portfolio.removeAsset(event.assetId(), event.price());
            portfolioRepository.save(portfolio);
        }
    }

}
