package com.usp.mac0499.modulartradingengine.trading.internal.infrastructure.repositories;

import com.usp.mac0499.modulartradingengine.trading.internal.domain.entities.Order;
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

    @Query("SELECT o FROM Order o WHERE o.portfolioId = :assetId AND o.status <> 'CANCELED'")
    List<Order> findValidOrdersByPortfolioId(@Param("portfolioId") UUID portfolioId);

}
