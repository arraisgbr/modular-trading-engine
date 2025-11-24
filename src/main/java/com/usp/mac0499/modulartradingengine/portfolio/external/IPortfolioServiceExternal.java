package com.usp.mac0499.modulartradingengine.portfolio.external;

import com.usp.mac0499.modulartradingengine.sharedkernel.events.*;

public interface IPortfolioServiceExternal {

    void executeTransaction(TransactionCompleted event);

    void releaseBalance(BuyOrderCancelled event);

    void releaseAsset(SellOrderCancelled event);

    void reserveBalance(BuyOrderCreated event);

    void reserveAsset(SellOrderCreated event);

    void removeDisabledAssetFromPortfolio(AssetDeleted event);

}