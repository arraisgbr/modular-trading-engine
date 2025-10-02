package com.usp.mac0499.modulartradingengine.portfolio.internal.api.mappers;

import com.usp.mac0499.modulartradingengine.portfolio.internal.api.dtos.request.MoneyRequest;
import com.usp.mac0499.modulartradingengine.portfolio.internal.domain.values.Money;
import org.springframework.stereotype.Component;

@Component
public class MoneyMapper {

    public Money toEntity(MoneyRequest request) {
        return new Money(request.value());
    }

}