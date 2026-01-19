package com.usp.mac0499.portfolio.service.interfaces;

import com.usp.mac0499.portfolio.domain.entities.Portfolio;
import com.usp.mac0499.sharedkernel.domain.values.Money;

import java.util.UUID;

public interface IPortfolioServiceInternal {

    Portfolio createPortfolio();

    Portfolio readPortfolio(UUID id);

    void deletePortfolio(UUID id);

    void depositValue(UUID id, Money amount);

    void withdrawalValue(UUID id, Money amount);

}