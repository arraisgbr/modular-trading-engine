package com.usp.mac0499.modulartradingengine.portfolio.internal.domain.entities;

import com.usp.mac0499.modulartradingengine.portfolio.internal.domain.exceptions.InsufficientBalanceException;
import com.usp.mac0499.modulartradingengine.sharedkernel.domain.values.Money;
import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.*;

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

    public void depositValue(Money valueToDeposit) {
        Objects.requireNonNull(valueToDeposit, "Value to deposit cannot be null.");
        this.availableBalance = this.availableBalance.add(valueToDeposit);
    }

    public void withdrawalValue(Money valueToWithdrawal) {
        Objects.requireNonNull(valueToWithdrawal, "Value to withdrawal cannot be null.");
        if (!this.availableBalance.isGreaterThanOrEqual(valueToWithdrawal)) {
            throw new InsufficientBalanceException("Insufficient available balance for withdrawal.");
        }
        this.availableBalance = this.availableBalance.subtract(valueToWithdrawal);
    }

    public void reserveBalance(Money valueToReserve) {
        Objects.requireNonNull(valueToReserve, "Value to reserve cannot be null.");
        if (!this.availableBalance.isGreaterThanOrEqual(valueToReserve)) {
            throw new InsufficientBalanceException("Insufficient available balance for reserve.");
        }
        this.availableBalance = this.availableBalance.subtract(valueToReserve);
        this.reservedBalance = this.reservedBalance.add(valueToReserve);
    }

    public void releaseBalance(Money valueToRelease) {
        Objects.requireNonNull(valueToRelease, "Value to release cannot be null.");
        if (!this.reservedBalance.isGreaterThanOrEqual(valueToRelease)) {
            throw new InsufficientBalanceException("Insufficient reserved balance to release.");
        }
        this.reservedBalance = this.reservedBalance.subtract(valueToRelease);
        this.availableBalance = this.availableBalance.add(valueToRelease);
    }

    public void reserveAsset(UUID assetId, Long quantity) {
        AssetBalance balance = findAssetBalance(assetId).orElseThrow(() -> new IllegalArgumentException("Portfolio does not hold asset " + assetId));
        balance.reserve(quantity);
    }

    public void releaseAsset(UUID assetId, Long quantity) {
        AssetBalance balance = findAssetBalance(assetId).orElseThrow(() -> new IllegalArgumentException("Portfolio does not hold asset " + assetId));
        balance.release(quantity);
    }

    public void executeDebtorTransaction(Money price, UUID assetId, Long quantity) {
        Objects.requireNonNull(price, "Price cannot be null.");
        if (!this.reservedBalance.isGreaterThanOrEqual(price)) {
            throw new InsufficientBalanceException("Insufficient reserved balance for transaction.");
        }
        this.reservedBalance = this.reservedBalance.subtract(price);

        this.findAssetBalance(assetId).orElseGet(() -> {
            AssetBalance newBalance = new AssetBalance(assetId, quantity);
            this.assets.add(newBalance);
            return newBalance;
        });
    }

    public void executeCreditorTransaction(Money price, UUID assetId, Long quantity) {
        Objects.requireNonNull(price, "Price cannot be null.");
        this.availableBalance = this.availableBalance.add(price);
        AssetBalance balance = findAssetBalance(assetId).orElseThrow(() -> new IllegalArgumentException("Portfolio does not hold asset to execute transaction: " + assetId));
        balance.debitReserved(quantity);
        if (balance.getAvailableQuantity() == 0 && balance.getReservedQuantity() == 0) {
            this.assets.remove(balance);
        }
    }

    public void removeAsset(UUID assetId, Money price) {
        Objects.requireNonNull(price, "Price cannot be null.");
        var assetsToRemove = this.assets.stream().filter(asset -> asset.getAssetId().equals(assetId)).toList();
        assetsToRemove.forEach(asset -> {
            this.availableBalance = this.availableBalance.add(price.multiply(new Money(BigDecimal.valueOf(asset.getAvailableQuantity()))));
            this.assets.remove(asset);
        });
    }

    private Optional<AssetBalance> findAssetBalance(UUID assetId) {
        return this.assets.stream().filter(asset -> asset.getAssetId().equals(assetId)).findFirst();
    }

}
