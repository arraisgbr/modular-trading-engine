package com.usp.mac0499.modulartradingengine.portfolio.external;

import java.math.BigDecimal;
import java.util.UUID;

public interface IPortfolioServiceExternal {

    void balanceReserved(UUID id, BigDecimal valueToReserve);

    void balanceReleased(UUID id, BigDecimal valueToRelease);

}