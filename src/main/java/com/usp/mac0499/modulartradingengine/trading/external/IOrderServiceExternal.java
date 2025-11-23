package com.usp.mac0499.modulartradingengine.trading.external;

import com.usp.mac0499.modulartradingengine.sharedkernel.events.AssetDeleted;
import com.usp.mac0499.modulartradingengine.sharedkernel.events.PortfolioDeleted;

public interface IOrderServiceExternal {

    void removeOrdersInvolvingAsset(AssetDeleted event);

    void removeOrdersInvolvingPortfolio(PortfolioDeleted event);

}