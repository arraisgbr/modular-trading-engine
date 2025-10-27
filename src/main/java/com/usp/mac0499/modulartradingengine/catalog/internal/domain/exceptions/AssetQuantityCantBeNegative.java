package com.usp.mac0499.modulartradingengine.catalog.internal.domain.exceptions;

import java.util.UUID;

public class AssetQuantityCantBeNegative extends RuntimeException {

    public AssetQuantityCantBeNegative(UUID id) {
        super("Asset quantity can't be negative with id: " + id);
    }

}
