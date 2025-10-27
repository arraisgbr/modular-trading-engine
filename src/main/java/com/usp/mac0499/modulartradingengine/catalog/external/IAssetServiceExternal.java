package com.usp.mac0499.modulartradingengine.catalog.external;

import java.math.BigDecimal;
import java.util.UUID;

public interface IAssetServiceExternal {

    void increaseQuantity(UUID id);

    void decreaseQuantity(UUID id);

    BigDecimal getValue(UUID id);

    void updateValue(UUID id, BigDecimal price);

}
