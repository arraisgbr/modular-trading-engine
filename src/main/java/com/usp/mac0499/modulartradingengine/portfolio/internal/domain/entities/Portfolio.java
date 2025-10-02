package com.usp.mac0499.modulartradingengine.portfolio.internal.domain.entities;

import com.usp.mac0499.modulartradingengine.portfolio.internal.domain.exceptions.InsufficientBalanceException;
import com.usp.mac0499.modulartradingengine.portfolio.internal.domain.values.Money;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

import java.util.UUID;

@Entity
@Table(name = "portfolios")
@Getter
public class Portfolio {

    @Id
    private UUID id;

    @Column(name = "available_balance")
    private Money availableBalance;

    @Column(name = "reserved_balance")
    private Money reservedBalance;

    public Portfolio() {
        this.id = UUID.randomUUID();
        this.availableBalance = Money.ZERO;
        this.reservedBalance = Money.ZERO;
    }

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

}
