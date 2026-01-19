package com.usp.mac0499.sharedkernel.domain.values;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class MoneyTest {

    @Test
    void shouldBeAbleToCreateMoney() {
        Money money = new Money(BigDecimal.valueOf(100));
        Assertions.assertThat(money.value()).isEqualByComparingTo(BigDecimal.valueOf(100));
    }

    @Test
    void shouldNotBeAbleToCreateMoneyWhenValueIsNegative() {
        Assertions.assertThatThrownBy(() -> new Money(BigDecimal.valueOf(-100))).isInstanceOf(IllegalArgumentException.class).hasMessage("Value cannot be negative.");
    }

    @Test
    void shouldNotBeAbleToCreateMoneyWhenValueIsNull() {
        Assertions.assertThatThrownBy(() -> new Money(null)).isInstanceOf(NullPointerException.class).hasMessage("Value cannot be null.");
    }

    @Test
    void shouldBeAbleToAddMoney() {
        Money money = new Money(BigDecimal.valueOf(100));
        Money otherMoney = new Money(BigDecimal.valueOf(200));
        Money result = money.add(otherMoney);
        Assertions.assertThat(result.value()).isEqualByComparingTo(BigDecimal.valueOf(300));
    }

    @Test
    void shouldNotBeAbleToAddMoneyWhenMoneyIsNull() {
        Money money = new Money(BigDecimal.valueOf(100));
        Assertions.assertThatThrownBy(() -> money.add(null)).isInstanceOf(NullPointerException.class).hasMessage("Value cannot be null.");
    }

    @Test
    void shouldBeAbleToSubtractMoney() {
        Money money = new Money(BigDecimal.valueOf(200));
        Money otherMoney = new Money(BigDecimal.valueOf(100));
        Money result = money.subtract(otherMoney);
        Assertions.assertThat(result.value()).isEqualByComparingTo(BigDecimal.valueOf(100));
    }

    @Test
    void shouldNotBeAbleToSubtractMoneyWhenResultsInNegative() {
        Money money = new Money(BigDecimal.valueOf(100));
        Money otherMoney = new Money(BigDecimal.valueOf(200));
        Assertions.assertThatThrownBy(() -> money.subtract(otherMoney)).isInstanceOf(IllegalArgumentException.class).hasMessage("Result value cannot be negative.");
    }

    @Test
    void shouldNotBeAbleToSubtractMoneyWhenMoneyIsNull() {
        Money money = new Money(BigDecimal.valueOf(100));
        Assertions.assertThatThrownBy(() -> money.subtract(null)).isInstanceOf(NullPointerException.class).hasMessage("Value cannot be null.");
    }

    @Test
    void shouldBeAbleToMultiplyMoney() {
        Money money = new Money(BigDecimal.valueOf(100));
        Money multiplier = new Money(BigDecimal.valueOf(2));
        Money result = money.multiply(multiplier);
        Assertions.assertThat(result.value()).isEqualByComparingTo(BigDecimal.valueOf(200));
    }

    @Test
    void shouldNotBeAbleToMultiplyMoneyWhenMoneyIsNull() {
        Money money = new Money(BigDecimal.valueOf(100));
        Assertions.assertThatThrownBy(() -> money.multiply(null)).isInstanceOf(NullPointerException.class).hasMessage("Value cannot be null.");
    }

    @Test
    void shouldBeAbleToCompareMoneyWhenGreaterThan() {
        Money money = new Money(BigDecimal.valueOf(200));
        Money otherMoney = new Money(BigDecimal.valueOf(100));
        Assertions.assertThat(money.isGreaterThanOrEqual(otherMoney)).isTrue();
    }

    @Test
    void shouldBeAbleToCompareMoneyWhenLessThan() {
        Money money = new Money(BigDecimal.valueOf(100));
        Money otherMoney = new Money(BigDecimal.valueOf(200));
        Assertions.assertThat(money.isGreaterThanOrEqual(otherMoney)).isFalse();
    }

    @Test
    void shouldBeAbleToCompareMoneyWhenEqual() {
        Money money = new Money(BigDecimal.valueOf(100));
        Money otherMoney = new Money(BigDecimal.valueOf(100));
        Assertions.assertThat(money.isGreaterThanOrEqual(otherMoney)).isTrue();
    }

    @Test
    void shouldNotBeAbleToCompareMoneyWhenMoneyIsNull() {
        Money money = new Money(BigDecimal.valueOf(100));
        Assertions.assertThatThrownBy(() -> money.isGreaterThanOrEqual(null)).isInstanceOf(NullPointerException.class).hasMessage("Value cannot be null.");
    }

}
