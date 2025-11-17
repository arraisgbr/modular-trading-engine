package com.usp.mac0499.modulartradingengine.trading.external;

import java.util.UUID;

public interface IOrderServiceExternal {

    void removeOrdersInvolvingAsset(UUID assetId);

    void removeOrdersInvolvingPortfolio(UUID portfolioId);

}