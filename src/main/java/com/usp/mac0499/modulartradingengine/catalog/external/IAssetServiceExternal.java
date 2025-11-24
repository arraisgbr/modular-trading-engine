package com.usp.mac0499.modulartradingengine.catalog.external;

import com.usp.mac0499.modulartradingengine.sharedkernel.events.TransactionCompleted;

public interface IAssetServiceExternal {

    void updateAsset(TransactionCompleted event);

}