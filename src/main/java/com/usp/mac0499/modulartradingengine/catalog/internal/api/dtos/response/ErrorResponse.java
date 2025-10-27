package com.usp.mac0499.modulartradingengine.catalog.internal.api.dtos.response;

import java.time.Instant;

public record ErrorResponse(
        int status,
        String error,
        String message,
        String path,
        Instant timestamp
) {
}