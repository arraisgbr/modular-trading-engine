package com.usp.mac0499.catalog.infrastructure.repositories;

import com.usp.mac0499.catalog.domain.entities.Asset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AssetRepository extends JpaRepository<Asset, UUID> {
}
