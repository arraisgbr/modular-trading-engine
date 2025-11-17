package com.usp.mac0499.modulartradingengine.catalog.internal.domain.entities;

import com.usp.mac0499.modulartradingengine.sharedkernel.domain.values.Money;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Optional;
import java.util.UUID;

@Entity
@Table(name = "assets")
@NoArgsConstructor
@Getter
public class Asset {

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
            this.price = this.price.update(slPrice);
        }, () -> {
            throw new IllegalArgumentException();
        });
    }
}
