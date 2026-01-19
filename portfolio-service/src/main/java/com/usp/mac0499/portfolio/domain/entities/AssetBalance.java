package com.usp.mac0499.portfolio.domain.entities;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Embeddable
@Getter
@NoArgsConstructor
public class AssetBalance {

    private UUID assetId;
    private Long availableQuantity;
    private Long reservedQuantity;

    public AssetBalance(UUID assetId, Long initialQuantity) {
        this.assetId = assetId;
        this.availableQuantity = initialQuantity;
        this.reservedQuantity = 0L;
    }

    public void reserve(Long quantity) {
        if (this.availableQuantity < quantity) {
            throw new IllegalStateException("Insufficient available assets to reserve for asset " + assetId);
        }
        this.availableQuantity -= quantity;
        this.reservedQuantity += quantity;
    }

    public void release(Long quantity) {
        if (this.reservedQuantity < quantity) {
            throw new IllegalStateException("Insufficient reserved assets to release for asset " + assetId);
        }
        this.reservedQuantity -= quantity;
        this.availableQuantity += quantity;
    }

    public void add(Long quantity) {
        this.availableQuantity += quantity;
    }

    public void debitReserved(Long quantity) {
        if (this.reservedQuantity < quantity) {
            throw new IllegalStateException("Insufficient reserved assets to debit for asset " + assetId);
        }
        this.reservedQuantity -= quantity;
    }

}
