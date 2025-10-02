package com.usp.mac0499.modulartradingengine.portfolio.internal.infrastructure.repositories;

import com.usp.mac0499.modulartradingengine.portfolio.internal.domain.entities.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PortfolioRepository extends JpaRepository<Portfolio, UUID> {
}