package com.usp.mac0499.trading.infrastructure.repositories;

import com.usp.mac0499.sharedkernel.domain.values.Money;
import com.usp.mac0499.sharedkernel.domain.values.OrderType;
import com.usp.mac0499.trading.domain.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {

    @Query("SELECT o FROM Order o WHERE o.id = :id AND o.status <> 'CANCELED'")
    Optional<Order> findValidOrderById(@Param("id") UUID id);

    @Query("SELECT o FROM Order o WHERE o.status <> 'CANCELED'")
    List<Order> findValidOrders();

    @Query("SELECT o FROM Order o WHERE o.assetId = :assetId AND o.status <> 'CANCELED'")
    List<Order> findValidOrdersByAssetId(@Param("assetId") UUID assetId);

    @Query("SELECT o FROM Order o WHERE o.portfolioId = :portfolioId AND o.status <> 'CANCELED'")
    List<Order> findValidOrdersByPortfolioId(@Param("portfolioId") UUID portfolioId);

    @Query("SELECT o FROM Order o WHERE o.assetId = :assetId AND o.price = :value AND o.type = :orderType AND o.status <> 'CANCELED'")
    Optional<Order> findOrderByAssetIdAndValueAndType(UUID assetId, Money value, OrderType orderType);

}
