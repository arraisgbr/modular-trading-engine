package com.usp.mac0499.catalog.api.dtos.request;


import com.usp.mac0499.sharedkernel.api.dtos.request.MoneyRequest;

public record AssetRequest(String code, MoneyRequest price) {
}
