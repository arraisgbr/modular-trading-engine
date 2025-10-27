package com.usp.mac0499.modulartradingengine.trading.internal.domain.values;

import java.math.BigDecimal;
import java.util.Objects;

public record Money(BigDecimal value) {

    public static final Money ZERO = new Money(BigDecimal.ZERO);

    public Money {
        Objects.requireNonNull(value, "Value cannot be null.");
        if (value.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Value cannot be negative.");
        }
    }

    public Money add(Money other) {
        Objects.requireNonNull(other, "Value cannot be null.");
        return new Money(this.value.add(other.value));
    }

    public Money subtract(Money other) {
        Objects.requireNonNull(other, "Value cannot be null.");
        return new Money(this.value.subtract(other.value));
    }

    public boolean isGreaterThanOrEqual(Money other) {
        Objects.requireNonNull(other, "Value cannot be null.");
        return this.value().compareTo((other.value())) >= 0;
    }

}