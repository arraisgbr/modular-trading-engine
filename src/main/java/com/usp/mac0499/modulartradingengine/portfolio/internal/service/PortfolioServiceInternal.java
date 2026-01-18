package com.usp.mac0499.modulartradingengine.portfolio.internal.service;

import com.usp.mac0499.modulartradingengine.portfolio.internal.domain.entities.Portfolio;
import com.usp.mac0499.modulartradingengine.portfolio.internal.domain.exceptions.PortfolioNotFoundException;
import com.usp.mac0499.modulartradingengine.portfolio.internal.infrastructure.repositories.PortfolioRepository;
import com.usp.mac0499.modulartradingengine.portfolio.internal.service.interfaces.IPortfolioServiceInternal;
import com.usp.mac0499.modulartradingengine.sharedkernel.domain.values.Money;
import com.usp.mac0499.modulartradingengine.trading.external.ITradingServiceExternal;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PortfolioServiceInternal implements IPortfolioServiceInternal {

    private final PortfolioRepository portfolioRepository;
    private final ITradingServiceExternal orderService;

    @Override
    public Portfolio createPortfolio() {
        return portfolioRepository.save(new Portfolio());
    }

    @Override
    public Portfolio readPortfolio(UUID id) {
        return portfolioRepository.findById(id).orElseThrow(() -> new PortfolioNotFoundException(id));
    }

    @Override
    public void deletePortfolio(UUID id) {
        portfolioRepository.findById(id).ifPresentOrElse(portfolio -> {
            portfolioRepository.delete(portfolio);
            orderService.removeOrdersInvolvingPortfolio(portfolio.getId());
        }, () -> {
            throw new PortfolioNotFoundException(id);
        });
    }

    @Override
    @Transactional
    public void depositValue(UUID id, Money amount) {
        Portfolio portfolio = portfolioRepository.findById(id).orElseThrow(() -> new PortfolioNotFoundException(id));
        portfolio.depositValue(amount);
    }

    @Override
    @Transactional
    public void withdrawalValue(UUID id, Money amount) {
        Portfolio portfolio = portfolioRepository.findById(id).orElseThrow(() -> new PortfolioNotFoundException(id));
        portfolio.withdrawalValue(amount);
    }

}