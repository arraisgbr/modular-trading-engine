package com.usp.mac0499.modulartradingengine.portfolio.internal.api.mappers;

import com.usp.mac0499.modulartradingengine.portfolio.internal.api.dtos.response.PortfolioResponse;
import com.usp.mac0499.modulartradingengine.portfolio.internal.domain.entities.Portfolio;
import com.usp.mac0499.modulartradingengine.sharedkernel.api.mappers.MoneyMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PortfolioMapper {

    private final MoneyMapper moneyMapper;

    public PortfolioResponse toResponse(Portfolio portfolio) {
        return new PortfolioResponse(portfolio.getId(), moneyMapper.toDto(portfolio.getAvailableBalance()), moneyMapper.toDto(portfolio.getReservedBalance()));
    }

}