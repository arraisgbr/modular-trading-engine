package com.usp.mac0499.modulartradingengine.trading.internal.domain.entities;

import com.usp.mac0499.modulartradingengine.sharedkernel.domain.values.OrderStatus;
import com.usp.mac0499.modulartradingengine.trading.internal.domain.exceptions.OrderCantBeCancelledException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class OrderTest {

    @Test
    void shouldBeAbleToCancelOrder() {
        Order order = new Order(UUID.randomUUID(), UUID.randomUUID(), 100L, null, null);
        order.cancelOrder();
        Assertions.assertThat(order.getStatus()).isEqualTo(OrderStatus.CANCELED);
    }

    @Test
    void shouldNotBeAbleToCancelCancelledOrder() {
        Order order = new Order(UUID.randomUUID(), UUID.randomUUID(), 100L, null, null);
        order.cancelOrder();
        Assertions.assertThatThrownBy(order::cancelOrder).isInstanceOf(OrderCantBeCancelledException.class).hasMessage("Order can't be cancelled with id: " + order.getId());
    }

}
