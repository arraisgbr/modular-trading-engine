package com.usp.mac0499.modulartradingengine.portfolio.internal.domain.entities;

import com.usp.mac0499.modulartradingengine.portfolio.internal.domain.exceptions.InsufficientBalanceException;
import com.usp.mac0499.modulartradingengine.sharedkernel.domain.values.Money;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

public class PortfolioTest {

    @Test
    void shouldBeAbleToDepositValue() {
        Portfolio portfolio = new Portfolio();
        Money depositValue = new Money(BigDecimal.valueOf(100));
        portfolio.depositValue(depositValue);
        Assertions.assertThat(portfolio.getAvailableBalance().value()).isEqualByComparingTo(BigDecimal.valueOf(100));
    }

    @Test
    void shouldNotBeAbleToDepositNullValue() {
        Portfolio portfolio = new Portfolio();
        Assertions.assertThatThrownBy(() -> portfolio.depositValue(null)).isInstanceOf(NullPointerException.class).hasMessage("Value to deposit cannot be null.");
    }

    @Test
    void shouldBeAbleToWithdrawalValue() {
        Portfolio portfolio = new Portfolio();
        portfolio.depositValue(new Money(BigDecimal.valueOf(200)));
        Money withdrawalValue = new Money(BigDecimal.valueOf(100));
        portfolio.withdrawalValue(withdrawalValue);
        Assertions.assertThat(portfolio.getAvailableBalance().value()).isEqualByComparingTo(BigDecimal.valueOf(100));
    }

    @Test
    void shouldNotBeAbleToWithdrawalValueWhenInsufficientBalance() {
        Portfolio portfolio = new Portfolio();
        Money withdrawalValue = new Money(BigDecimal.valueOf(100));
        Assertions.assertThatThrownBy(() -> portfolio.withdrawalValue(withdrawalValue)).isInstanceOf(InsufficientBalanceException.class).hasMessage("Insufficient available balance for withdrawal.");
    }

    @Test
    void shouldNotBeAbleToWithdrawalNullValue() {
        Portfolio portfolio = new Portfolio();
        Assertions.assertThatThrownBy(() -> portfolio.withdrawalValue(null)).isInstanceOf(NullPointerException.class).hasMessage("Value to withdrawal cannot be null.");
    }

    @Test
    void shouldBeAbleToReserveBalance() {
        Portfolio portfolio = new Portfolio();
        portfolio.depositValue(new Money(BigDecimal.valueOf(200)));
        Money reserveValue = new Money(BigDecimal.valueOf(100));
        portfolio.reserveBalance(reserveValue);
        Assertions.assertThat(portfolio.getAvailableBalance().value()).isEqualByComparingTo(BigDecimal.valueOf(100));
        Assertions.assertThat(portfolio.getReservedBalance().value()).isEqualByComparingTo(BigDecimal.valueOf(100));
    }

    @Test
    void shouldNotBeAbleToReserveBalanceWhenInsufficientBalance() {
        Portfolio portfolio = new Portfolio();
        Money reserveValue = new Money(BigDecimal.valueOf(100));
        Assertions.assertThatThrownBy(() -> portfolio.reserveBalance(reserveValue)).isInstanceOf(InsufficientBalanceException.class).hasMessage("Insufficient available balance for reserve.");
    }

    @Test
    void shouldNotBeAbleToReserveBalanceWhenValueNull() {
        Portfolio portfolio = new Portfolio();
        Assertions.assertThatThrownBy(() -> portfolio.reserveBalance(null)).isInstanceOf(NullPointerException.class).hasMessage("Value to reserve cannot be null.");
    }

    @Test
    void shouldBeAbleToReleaseBalance() {
        Portfolio portfolio = new Portfolio();
        Money depositValue = new Money(BigDecimal.valueOf(200));
        Money releaseValue = new Money(BigDecimal.valueOf(100));
        portfolio.depositValue(depositValue);
        portfolio.reserveBalance(releaseValue);
        portfolio.releaseBalance(releaseValue);
        Assertions.assertThat(portfolio.getAvailableBalance().value()).isEqualByComparingTo(BigDecimal.valueOf(200));
    }

    @Test
    void shouldNotBeAbleToReleaseBalanceWhenInsufficientBalance() {
        Portfolio portfolio = new Portfolio();
        Money depositValue = new Money(BigDecimal.valueOf(200));
        Money releaseValue = new Money(BigDecimal.valueOf(100));
        portfolio.depositValue(depositValue);
        portfolio.reserveBalance(releaseValue);
        portfolio.releaseBalance(releaseValue);
        Assertions.assertThat(portfolio.getAvailableBalance().value()).isEqualByComparingTo(BigDecimal.valueOf(200));
    }

    @Test
    void shouldNotBeAbleToReleaseBalanceWhenValueNull() {
        Portfolio portfolio = new Portfolio();
        Assertions.assertThatThrownBy(() -> portfolio.releaseBalance(null)).isInstanceOf(NullPointerException.class).hasMessage("Value to release cannot be null.");
    }

    @Test
    void shouldBeAbleToReserveAsset() {
        Portfolio portfolio = new Portfolio();
        UUID assetId = UUID.randomUUID();
        portfolio.executeDebtorTransaction(Money.ZERO, assetId, 10L);
        portfolio.reserveAsset(assetId, 10L);
        AssetBalance asset = portfolio.getAssets().stream().filter(asst -> asst.getAssetId().equals(assetId)).findFirst().get();
        Assertions.assertThat(asset.getReservedQuantity()).isEqualTo(10L);
    }

    @Test
    void shouldNotBeAbleToReserveInexistentAsset() {
        Portfolio portfolio = new Portfolio();
        UUID assetId = UUID.randomUUID();
        Assertions.assertThatThrownBy(() -> portfolio.reserveAsset(assetId, 10L)).isInstanceOf(IllegalArgumentException.class).hasMessage("Portfolio does not hold asset " + assetId);
    }

    @Test
    void shouldBeAbleToReleaseAsset() {
        Portfolio portfolio = new Portfolio();
        UUID assetId = UUID.randomUUID();
        portfolio.executeDebtorTransaction(Money.ZERO, assetId, 10L);
        portfolio.reserveAsset(assetId, 10L);
        portfolio.releaseAsset(assetId, 10L);
        AssetBalance asset = portfolio.getAssets().stream().filter(asst -> asst.getAssetId().equals(assetId)).findFirst().get();
        Assertions.assertThat(asset.getAvailableQuantity()).isEqualTo(10L);
    }

    @Test
    void shouldNotBeAbleToReleaseInexistentAsset() {
        Portfolio portfolio = new Portfolio();
        UUID assetId = UUID.randomUUID();
        Assertions.assertThatThrownBy(() -> portfolio.releaseAsset(assetId, 10L)).isInstanceOf(IllegalArgumentException.class).hasMessage("Portfolio does not hold asset " + assetId);
    }

    @Test
    void shouldBeAbleToExecuteDebtorTransaction() {
        Portfolio portfolio = new Portfolio();
        UUID assetId = UUID.randomUUID();
        portfolio.executeDebtorTransaction(Money.ZERO, assetId, 10L);
        AssetBalance asset = portfolio.getAssets().stream().filter(asst -> asst.getAssetId().equals(assetId)).findFirst().get();
        Assertions.assertThat(portfolio.getReservedBalance().value()).isEqualTo(BigDecimal.ZERO);
        Assertions.assertThat(asset.getAvailableQuantity()).isEqualTo(10L);
    }

    @Test
    void shouldNotBeAbleToExecuteDebtorTransactionWhenInsufficientBalance() {
        Portfolio portfolio = new Portfolio();
        UUID assetId = UUID.randomUUID();
        Assertions.assertThatThrownBy(() -> portfolio.executeDebtorTransaction(new Money(BigDecimal.valueOf(100)), assetId, 10L)).isInstanceOf(InsufficientBalanceException.class).hasMessage("Insufficient reserved balance for transaction.");
    }

    @Test
    void shouldNotBeAbleToExecuteDebtorTransactionWhenPriceIsNull() {
        Portfolio portfolio = new Portfolio();
        Assertions.assertThatThrownBy(() -> portfolio.executeDebtorTransaction(null, UUID.randomUUID(), 100L)).isInstanceOf(NullPointerException.class).hasMessage("Price cannot be null.");
    }

    @Test
    void shouldBeAbleToExecuteCreditorTransaction() {
        Portfolio portfolio = new Portfolio();
        UUID assetId = UUID.randomUUID();
        portfolio.executeDebtorTransaction(Money.ZERO, assetId, 10L);
        portfolio.reserveAsset(assetId, 10L);
        portfolio.executeCreditorTransaction(new Money(BigDecimal.valueOf(100)), assetId, 10L);
        Optional<AssetBalance> asset = portfolio.getAssets().stream().filter(asst -> asst.getAssetId().equals(assetId)).findFirst();
        Assertions.assertThat(portfolio.getAvailableBalance().value()).isEqualTo(BigDecimal.valueOf(100));
        Assertions.assertThat(asset).isEmpty();
    }

    @Test
    void shouldNotBeAbleToExecuteCreditorTransactionWhenInsufficientAssets() {
        Portfolio portfolio = new Portfolio();
        UUID assetId = UUID.randomUUID();
        Assertions.assertThatThrownBy(() -> portfolio.executeCreditorTransaction(Money.ZERO, assetId, 10L)).isInstanceOf(IllegalArgumentException.class).hasMessage("Portfolio does not hold asset to execute transaction: " + assetId);
    }

    @Test
    void shouldNotBeAbleToExecuteCreditorTransactionWhenPriceIsNull() {
        Portfolio portfolio = new Portfolio();
        Assertions.assertThatThrownBy(() -> portfolio.executeCreditorTransaction(null, UUID.randomUUID(), 100L)).isInstanceOf(NullPointerException.class).hasMessage("Price cannot be null.");
    }

    @Test
    void shouldBeAbleToRemoveAsset() {
        Portfolio portfolio = new Portfolio();
        UUID assetId = UUID.randomUUID();
        Money value = new Money(BigDecimal.valueOf(100));
        portfolio.depositValue(value);
        portfolio.reserveBalance(value);
        portfolio.executeDebtorTransaction(value, assetId, 1L);
        portfolio.removeAsset(assetId, value);
        Optional<AssetBalance> asset = portfolio.getAssets().stream().filter(asst -> asst.getAssetId().equals(assetId)).findFirst();
        Assertions.assertThat(portfolio.getAvailableBalance()).isEqualTo(value);
        Assertions.assertThat(asset).isEmpty();
    }

    @Test
    void shouldNotBeAbleToRemoveAssetWhenPriceIsNull() {
        Portfolio portfolio = new Portfolio();
        Assertions.assertThatThrownBy(() -> portfolio.removeAsset(UUID.randomUUID(), null)).isInstanceOf(NullPointerException.class).hasMessage("Price cannot be null.");
    }

}
