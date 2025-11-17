package com.usp.mac0499.modulartradingengine.portfolio.internal.domain.entities;

import com.usp.mac0499.modulartradingengine.portfolio.internal.domain.exceptions.InsufficientBalanceException;
import com.usp.mac0499.modulartradingengine.sharedkernel.domain.values.Money;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Entity
@Table(name = "portfolios")
@Getter
public class Portfolio {

    @Id
    private UUID id;

    @Embedded
    @AttributeOverrides({@AttributeOverride(name = "value", column = @Column(name = "available_balance"))})
    private Money availableBalance;

    @Embedded
    @AttributeOverrides({@AttributeOverride(name = "value", column = @Column(name = "reserved_balance"))})
    private Money reservedBalance;

    @ElementCollection
    @CollectionTable(name = "portfolio_assets", joinColumns = @JoinColumn(name = "portfolio_id"))
    private List<AssetBalance> assets;

    public Portfolio() {
        this.id = UUID.randomUUID();
        this.availableBalance = Money.ZERO;
        this.reservedBalance = Money.ZERO;
        this.assets = new ArrayList<>();
    }

    // Balance methods remain the same...
    public void depositValue(Money valueToDeposit) {
        this.availableBalance = this.availableBalance.add(valueToDeposit);
    }

    public void withdrawalValue(Money valueToWithdrawal) {
        if (!this.availableBalance.isGreaterThanOrEqual(valueToWithdrawal)) {
            throw new InsufficientBalanceException("Insufficient available balance for withdrawal.");
        }
        this.availableBalance = this.availableBalance.subtract(valueToWithdrawal);
    }

    public void reserveBalance(Money valueToReserve) {
        if (!this.availableBalance.isGreaterThanOrEqual(valueToReserve)) {
            throw new InsufficientBalanceException("Insufficient available balance for reserve.");
        }
        this.availableBalance = this.availableBalance.subtract(valueToReserve);
        this.reservedBalance = this.reservedBalance.add(valueToReserve);
    }

    public void releaseBalance(Money valueToRelease) {
        if (!this.reservedBalance.isGreaterThanOrEqual(valueToRelease)) {
            throw new InsufficientBalanceException("Insufficient reserved balance to release.");
        }
        this.reservedBalance = this.reservedBalance.subtract(valueToRelease);
        this.availableBalance = this.availableBalance.add(valueToRelease);
    }

    public void reserveAsset(UUID assetId, Long quantity) {
        AssetBalance balance = findAssetBalance(assetId).orElseThrow(() -> new IllegalStateException("Portfolio does not hold asset " + assetId));
        balance.reserve(quantity);
    }

    public void releaseAsset(UUID assetId, Long quantity) {
        AssetBalance balance = findAssetBalance(assetId).orElseThrow(() -> new IllegalStateException("Portfolio does not hold asset " + assetId));
        balance.release(quantity);
    }

    public void executeDebtorTransaction(Money price, UUID assetId, Long quantity) {
        if (!this.reservedBalance.isGreaterThanOrEqual(price)) {
            throw new InsufficientBalanceException("Insufficient reserved balance for transaction.");
        }
        this.reservedBalance = this.reservedBalance.subtract(price);

        AssetBalance balance = findAssetBalance(assetId).orElseGet(() -> {
            AssetBalance newBalance = new AssetBalance(assetId, 0L);
            this.assets.add(newBalance);
            return newBalance;
        });
        balance.add(quantity);
    }

    public void executeCreditorTransaction(Money price, UUID assetId, Long quantity) {
        this.availableBalance = this.availableBalance.add(price);

        AssetBalance balance = findAssetBalance(assetId).orElseThrow(() -> new IllegalStateException("Portfolio does not hold asset to execute transaction: " + assetId));
        balance.debitReserved(quantity);
    }

    public void removeAsset(UUID assetId) {
        this.assets.removeIf(asset -> asset.getAssetId().equals(assetId));
    }

    private Optional<AssetBalance> findAssetBalance(UUID assetId) {
        return this.assets.stream().filter(asset -> asset.getAssetId().equals(assetId)).findFirst();
    }

}
