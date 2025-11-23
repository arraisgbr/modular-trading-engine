package com.usp.mac0499.modulartradingengine.catalog.internal.api.dtos.request;

import com.usp.mac0499.modulartradingengine.sharedkernel.api.dtos.request.MoneyRequest;

public record AssetRequest(String code, MoneyRequest price) {
}
