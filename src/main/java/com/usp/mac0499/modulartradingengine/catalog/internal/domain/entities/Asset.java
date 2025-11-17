package com.usp.mac0499.modulartradingengine.catalog.internal.domain.entities;

import com.usp.mac0499.modulartradingengine.sharedkernel.domain.values.Money;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Entity
@Table(name = "assets")
@NoArgsConstructor
@Getter
public class Asset {

    private final double PRICE_ADJUSTMENT_FACTOR = 0.15;

    @Id
    private UUID id;

    @Column(unique = true)
    private String code;

    @Embedded
    private Money price;

    public Asset(String code, Money price, Integer quantity) {
        this.id = UUID.randomUUID();
        this.code = code;
        this.price = price;
    }

    public void updatePrice(Money salePrice) {
        Optional.ofNullable(salePrice).ifPresentOrElse(slPrice -> {
            var adjustmentFactor = new Money(BigDecimal.valueOf(PRICE_ADJUSTMENT_FACTOR));
            this.price = this.price.isGreaterThanOrEqual(slPrice)
                    ? this.price.subtract(this.price.subtract(slPrice).multiply(adjustmentFactor))
                    : this.price.add(slPrice.subtract(this.price).multiply(adjustmentFactor));
        }, () -> {
            throw new IllegalArgumentException();
        });
    }
}
