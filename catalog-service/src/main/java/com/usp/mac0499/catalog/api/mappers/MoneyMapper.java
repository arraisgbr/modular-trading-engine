package com.usp.mac0499.catalog.api.mappers;

import com.usp.mac0499.sharedkernel.api.dtos.request.MoneyRequest;
import com.usp.mac0499.sharedkernel.api.dtos.response.MoneyResponse;
import com.usp.mac0499.sharedkernel.domain.values.Money;
import org.springframework.stereotype.Component;

@Component
public class MoneyMapper {

    public Money toEntity(MoneyRequest request) {
        return new Money(request.value());
    }

    public MoneyResponse toDto(Money money) {
        return new MoneyResponse(money.value());
    }

}
