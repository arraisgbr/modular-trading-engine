package com.usp.mac0499.portfolio.api.mappers;

import com.usp.mac0499.portfolio.api.dtos.response.PortfolioResponse;
import com.usp.mac0499.portfolio.domain.entities.Portfolio;
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