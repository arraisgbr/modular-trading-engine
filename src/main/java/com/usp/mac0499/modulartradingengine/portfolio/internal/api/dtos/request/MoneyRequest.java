package com.usp.mac0499.modulartradingengine.portfolio.internal.api.dtos.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record MoneyRequest(@NotNull @Positive BigDecimal value) {
}