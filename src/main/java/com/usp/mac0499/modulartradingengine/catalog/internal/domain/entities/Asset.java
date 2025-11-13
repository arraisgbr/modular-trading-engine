package com.usp.mac0499.modulartradingengine.catalog.internal.domain.entities;

import com.usp.mac0499.modulartradingengine.catalog.internal.domain.exceptions.AssetQuantityCantBeNegative;
import com.usp.mac0499.modulartradingengine.sharedkernel.domain.values.Money;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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

    @Column(name = "code", unique = true)
    private String code;

    @Column(name = "price")
    private Money price;

    @Column(name = "quantity")
    private Integer quantity;

    public Asset(String code, Money price, Integer quantity) {
        this.id = UUID.randomUUID();
        this.code = code;
        this.price = price;
        this.quantity = quantity;
    }

    public void increaseQuantity() {
        this.quantity++;
    }

    public void decreaseQuantity() {
        if (this.quantity == 0) {
            throw new AssetQuantityCantBeNegative(this.id);
        }
        this.quantity--;
    }

    public void setPrice(Money newPrice) {
        Optional.ofNullable(newPrice).ifPresentOrElse(price -> this.price = price, () -> {
            throw new IllegalArgumentException();
        });
    }
}
