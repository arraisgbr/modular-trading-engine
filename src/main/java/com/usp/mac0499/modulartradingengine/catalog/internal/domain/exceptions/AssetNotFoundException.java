package com.usp.mac0499.modulartradingengine.catalog.internal.domain.exceptions;

import java.util.UUID;

public class AssetNotFoundException extends RuntimeException {

    public AssetNotFoundException(UUID id) {
        super("Asset not found with id: " + id);
    }

}
