package com.usp.mac0499.catalog.api.dtos.response;


import com.usp.mac0499.sharedkernel.api.dtos.response.MoneyResponse;

import java.util.UUID;

public record AssetResponse(UUID id, String code, MoneyResponse price) {
}
