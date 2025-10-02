package com.usp.mac0499.modulartradingengine.portfolio.internal.service;

import com.usp.mac0499.modulartradingengine.portfolio.external.IPortfolioService;
import com.usp.mac0499.modulartradingengine.portfolio.internal.domain.entities.Portfolio;
import com.usp.mac0499.modulartradingengine.portfolio.internal.domain.exceptions.PortfolioNotFoundException;
import com.usp.mac0499.modulartradingengine.portfolio.internal.domain.values.Money;
import com.usp.mac0499.modulartradingengine.portfolio.internal.infrastructure.repositories.PortfolioRepository;
import com.usp.mac0499.modulartradingengine.portfolio.internal.service.interfaces.IPortfolioServiceInternal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PortfolioService implements IPortfolioServiceInternal, IPortfolioService {

    private final PortfolioRepository portfolioRepository;

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
        portfolioRepository.findById(id).ifPresentOrElse(portfolioRepository::delete, () -> {
            throw new PortfolioNotFoundException(id);
        });
    }

    @Override
    public void depositValue(UUID id, Money valueToDeposit) {
        portfolioRepository.findById(id).ifPresentOrElse(portfolio -> {
            portfolio.depositValue(valueToDeposit);
            portfolioRepository.save(portfolio);
        }, () -> {
            throw new PortfolioNotFoundException(id);
        });
    }

    @Override
    public void withdrawalValue(UUID id, Money valueToWithdrawal) {
        portfolioRepository.findById(id).ifPresentOrElse(portfolio -> {
            portfolio.withdrawalValue(valueToWithdrawal);
            portfolioRepository.save(portfolio);
        }, () -> {
            throw new PortfolioNotFoundException(id);
        });
    }

    @Override
    public void balanceReserved(UUID id, BigDecimal valueToReserve) {
        portfolioRepository.findById(id).ifPresentOrElse(portfolio -> {
            portfolio.reserveBalance(new Money(valueToReserve));
            portfolioRepository.save(portfolio);
        }, () -> {
            throw new PortfolioNotFoundException(id);
        });
    }

    @Override
    public void balanceReleased(UUID id, BigDecimal valueToRelease) {
        portfolioRepository.findById(id).ifPresentOrElse(portfolio -> {
            portfolio.releaseBalance(new Money(valueToRelease));
            portfolioRepository.save(portfolio);
        }, () -> {
            throw new PortfolioNotFoundException(id);
        });
    }
}