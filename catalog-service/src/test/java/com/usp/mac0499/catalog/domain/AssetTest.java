package com.usp.mac0499.catalog.domain;

import com.usp.mac0499.catalog.domain.entities.Asset;
import com.usp.mac0499.sharedkernel.domain.values.Money;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class AssetTest {

    @Test
    void shouldBeAbleToUpdatePriceToHigherValue() {
        Money originalPrice = new Money(BigDecimal.valueOf(100));
        Money salePrice = new Money(BigDecimal.valueOf(150));
        Asset asset = new Asset("Asset", originalPrice);
        asset.updatePrice(salePrice);
        Assertions.assertThat(asset.getPrice().value()).isEqualByComparingTo(BigDecimal.valueOf(107.50));
    }

    @Test
    void shouldBeAbleToUpdatePriceToLowerValue() {
        Money originalPrice = new Money(BigDecimal.valueOf(100));
        Money salePrice = new Money(BigDecimal.valueOf(50));
        Asset asset = new Asset("Asset", originalPrice);
        asset.updatePrice(salePrice);
        Assertions.assertThat(asset.getPrice().value()).isEqualByComparingTo(BigDecimal.valueOf(92.50));
    }

    @Test
    void shouldNotBeAbleToUpdatePriceWhenSalePriceIsNull() {
        Money originalPrice = new Money(BigDecimal.valueOf(100));
        Asset asset = new Asset("Asset", originalPrice);
        Assertions.assertThatThrownBy(() -> asset.updatePrice(null)).isInstanceOf(NullPointerException.class).hasMessage("Sale price cannot be null.");
    }

}
