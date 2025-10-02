package com.usp.mac0499.modulartradingengine.portfolio.internal.api.mappers;

import com.usp.mac0499.modulartradingengine.portfolio.internal.api.dtos.response.PortfolioResponse;
import com.usp.mac0499.modulartradingengine.portfolio.internal.domain.entities.Portfolio;
import org.springframework.stereotype.Component;

@Component
public class PortfolioMapper {

    public PortfolioResponse toResponse(Portfolio portfolio) {
        return new PortfolioResponse(
                portfolio.getId(),
                portfolio.getAvailableBalance().value(),
                portfolio.getReservedBalance().value()
        );
    }

}