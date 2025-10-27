package com.usp.mac0499.modulartradingengine.trading.internal.domain.entities;

import com.usp.mac0499.modulartradingengine.trading.internal.domain.enums.OrderStatus;
import com.usp.mac0499.modulartradingengine.trading.internal.domain.enums.OrderType;
import com.usp.mac0499.modulartradingengine.trading.internal.domain.exceptions.OrderCantBeCancelledException;
import com.usp.mac0499.modulartradingengine.trading.internal.domain.values.Money;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "orders")
@NoArgsConstructor
@Getter
public class Order {

    @Id
    private UUID id;

    @Column(name = "portfolio_id")
    private UUID portfolioId;

    @Column(name = "asset_id")
    private UUID assetId;

    @Column(name = "quantity")
    private Long quantity;

    @Column(name = "price")
    private Money price;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private OrderType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatus status;

    public Order(UUID portfolioId, UUID assetId, Long quantity, Money price, OrderType type) {
        this.id = UUID.randomUUID();
        this.portfolioId = portfolioId;
        this.assetId = assetId;
        this.quantity = quantity;
        this.price = price;
        this.type = type;
        this.status = OrderStatus.OPEN;
    }

    public void cancelOrder() {
        if (this.status != OrderStatus.OPEN) {
            throw new OrderCantBeCancelledException(id);
        }
        this.status = OrderStatus.CANCELED;
    }

}
