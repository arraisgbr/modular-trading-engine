package com.usp.mac0499.modulartradingengine.portfolio.external;

import com.usp.mac0499.modulartradingengine.sharedkernel.domain.values.Money;

import java.util.UUID;

public interface IPortfolioServiceExternal {

    void executeTransaction(UUID debtorId, UUID creditorId, UUID assetId, Long assetQuantity, Money price);

    void releaseBalance(UUID portfolioId, Money amount);

    void releaseAsset(UUID portfolioId, UUID assetId, Long quantity);

    void reserveBalance(UUID portfolioId, Money amount);

    void reserveAsset(UUID portfolioId, UUID assetId, Long quantity);

    void removeDisabledAssetFromPortfolio(UUID assetId, Money price);

}