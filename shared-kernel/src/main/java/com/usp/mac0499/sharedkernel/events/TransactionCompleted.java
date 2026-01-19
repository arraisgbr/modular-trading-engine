package com.usp.mac0499.sharedkernel.events;


import com.usp.mac0499.sharedkernel.domain.values.Money;
import org.springframework.modulith.events.Externalized;

import java.util.UUID;

@Externalized(target = MessageBrokerConstants.TRANSACTION_COMPLETED_EXCHANGE)
public record TransactionCompleted(UUID debtorPortfolioId, UUID creditorPortfolioId, UUID assetId, Long quantity,
                                   Money price) {
}