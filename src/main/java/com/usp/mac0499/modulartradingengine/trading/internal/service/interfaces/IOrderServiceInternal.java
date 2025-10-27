
package com.usp.mac0499.modulartradingengine.trading.internal.service.interfaces;

import com.usp.mac0499.modulartradingengine.trading.internal.domain.entities.Order;
import com.usp.mac0499.modulartradingengine.trading.internal.domain.enums.OrderType;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface IOrderServiceInternal {

    Order createOrder(UUID portfolioId, UUID assetId, BigDecimal price, Long quantity, OrderType type);

    Order readOrder(UUID id);

    List<Order> readOrders();

    void cancelOrder(UUID id);

}
