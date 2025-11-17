package com.usp.mac0499.modulartradingengine.portfolio.internal.infrastructure.repositories;

import com.usp.mac0499.modulartradingengine.portfolio.internal.domain.entities.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, UUID> {

    List<Portfolio> findByAssets_AssetId(UUID assetId);

}