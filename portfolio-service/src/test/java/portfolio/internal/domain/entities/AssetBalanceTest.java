package portfolio.internal.domain.entities;

import com.usp.mac0499.portfolio.domain.entities.AssetBalance;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class AssetBalanceTest {

    @Test
    public void shouldBeAbleToReserveAsset() {
        AssetBalance assetBalance = new AssetBalance(UUID.randomUUID(), 100L);
        assetBalance.reserve(50L);
        Assertions.assertThat(assetBalance.getReservedQuantity()).isEqualTo(50);
    }

    @Test
    public void shouldNotBeAbleToReserveAssetWhenInsufficientQuantity() {
        AssetBalance assetBalance = new AssetBalance(UUID.randomUUID(), 100L);
        Assertions.assertThatThrownBy(() -> assetBalance.reserve(150L)).isInstanceOf(IllegalStateException.class).hasMessage("Insufficient available assets to reserve for asset " + assetBalance.getAssetId());
    }

    @Test
    public void shouldBeAbleToReleaseAsset() {
        AssetBalance assetBalance = new AssetBalance(UUID.randomUUID(), 100L);
        assetBalance.reserve(50L);
        assetBalance.release(50L);
        Assertions.assertThat(assetBalance.getReservedQuantity()).isEqualTo(0);
    }

    @Test
    public void shouldNotBeAbleToReleaseAssetWhenInsufficientQuantity() {
        AssetBalance assetBalance = new AssetBalance(UUID.randomUUID(), 100L);
        Assertions.assertThatThrownBy(() -> assetBalance.release(150L)).isInstanceOf(IllegalStateException.class).hasMessage("Insufficient reserved assets to release for asset " + assetBalance.getAssetId());
    }

    @Test
    public void shouldBeAbleToAddQuantity() {
        AssetBalance assetBalance = new AssetBalance(UUID.randomUUID(), 100L);
        assetBalance.add(50L);
        Assertions.assertThat(assetBalance.getAvailableQuantity()).isEqualTo(150L);
    }

    @Test
    public void shouldBeAbleToDebitReservedQuantity() {
        AssetBalance assetBalance = new AssetBalance(UUID.randomUUID(), 100L);
        assetBalance.reserve(50L);
        assetBalance.debitReserved(50L);
        Assertions.assertThat(assetBalance.getAvailableQuantity()).isEqualTo(50L);
        Assertions.assertThat(assetBalance.getReservedQuantity()).isEqualTo(0);
    }

}
