package com.usp.mac0499.modulartradingengine.catalog.internal.infrastructure.repositories;

import com.usp.mac0499.modulartradingengine.catalog.internal.domain.entities.Asset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AssetRepository extends JpaRepository<Asset, UUID> {
}
