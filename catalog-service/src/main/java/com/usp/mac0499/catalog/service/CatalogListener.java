package com.usp.mac0499.catalog.service;

import com.usp.mac0499.catalog.infrastructure.repositories.AssetRepository;
import com.usp.mac0499.sharedkernel.events.TransactionCompleted;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CatalogListener {

    private final AssetRepository assetRepository;

    @RabbitListener(queues = "catalog.transactionCompleted.queue")
    public void updateAsset(TransactionCompleted event) {
        assetRepository.findById(event.assetId()).ifPresent(asset -> {
            asset.updatePrice(event.price());
            assetRepository.save(asset);
        });
    }

}
