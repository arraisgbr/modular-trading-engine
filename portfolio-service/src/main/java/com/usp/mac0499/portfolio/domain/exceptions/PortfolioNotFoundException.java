package com.usp.mac0499.portfolio.domain.exceptions;

import java.util.UUID;

public class PortfolioNotFoundException extends RuntimeException {

    public PortfolioNotFoundException(UUID id) {
        super("Portfolio not found with id: " + id);
    }

}