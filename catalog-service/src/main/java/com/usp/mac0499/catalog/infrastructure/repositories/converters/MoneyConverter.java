package com.usp.mac0499.catalog.infrastructure.repositories.converters;

import com.usp.mac0499.sharedkernel.domain.values.Money;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.math.BigDecimal;
import java.util.Objects;

@Converter(autoApply = true)
public class MoneyConverter implements AttributeConverter<Money, BigDecimal> {

    @Override
    public BigDecimal convertToDatabaseColumn(Money money) {
        return Objects.isNull(money) ? null : money.value();
    }

    @Override
    public Money convertToEntityAttribute(BigDecimal value) {
        return Objects.isNull(value) ? null : new Money(value);
    }

}