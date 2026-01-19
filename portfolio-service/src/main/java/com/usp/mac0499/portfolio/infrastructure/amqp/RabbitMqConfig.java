package com.usp.mac0499.portfolio.infrastructure.amqp;

import com.usp.mac0499.sharedkernel.events.MessageBrokerConstants;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    @Bean
    public Declarables tradingBindings() {

        var transactionCompletedQueue = new Queue("portfolio.transactionCompleted.queue");
        var buyOrderCancelledQueue = new Queue("portfolio.buyOrderCancelled.queue");
        var sellOrderCancelledQueue = new Queue("portfolio.sellOrderCancelled.queue");
        var buyOrderCreatedQueue = new Queue("portfolio.buyOrderCreated.queue");
        var sellOrderCreatedQueue = new Queue("portfolio.sellOrderCreated.queue");
        var assetDeletedQueue = new Queue("portfolio.assetDeleted.queue");

        var transactionCompletedExchange = new TopicExchange(MessageBrokerConstants.TRANSACTION_COMPLETED_EXCHANGE);
        var buyOrderCancelledExchange = new TopicExchange(MessageBrokerConstants.BUY_ORDER_CANCELLED_EXCHANGE);
        var sellOrderCancelledExchange = new TopicExchange(MessageBrokerConstants.SELL_ORDER_CANCELLED_EXCHANGE);
        var buyOrderCreatedExchange = new TopicExchange(MessageBrokerConstants.BUY_ORDER_CREATED_EXCHANGE);
        var sellOrderCreatedExchange = new TopicExchange(MessageBrokerConstants.SELL_ORDER_CREATED_EXCHANGE);
        var assetDeletedExchange = new TopicExchange(MessageBrokerConstants.ASSET_DELETED_EXCHANGE);

        var bindingTransactionCompleted = BindingBuilder
                .bind(transactionCompletedQueue)
                .to(transactionCompletedExchange)
                .with("#");
        var bindingBuyOrderCancelled = BindingBuilder
                .bind(buyOrderCancelledQueue)
                .to(buyOrderCancelledExchange)
                .with("#");
        var bindingSellOrderCancelled = BindingBuilder
                .bind(sellOrderCancelledQueue)
                .to(sellOrderCancelledExchange)
                .with("#");
        var bindingBuyOrderCreated = BindingBuilder
                .bind(buyOrderCreatedQueue)
                .to(buyOrderCreatedExchange)
                .with("#");
        var bindingSellOrderCreated = BindingBuilder
                .bind(sellOrderCreatedQueue)
                .to(sellOrderCreatedExchange)
                .with("#");
        var bindingAssetDeleted = BindingBuilder
                .bind(assetDeletedQueue)
                .to(assetDeletedExchange)
                .with("#");


        return new Declarables(transactionCompletedQueue, buyOrderCancelledQueue, sellOrderCancelledQueue, buyOrderCreatedQueue,
                sellOrderCreatedQueue, assetDeletedQueue, transactionCompletedExchange, buyOrderCancelledExchange,
                sellOrderCancelledExchange, buyOrderCreatedExchange, sellOrderCreatedExchange, assetDeletedExchange,
                bindingTransactionCompleted, bindingBuyOrderCancelled, bindingSellOrderCancelled, bindingBuyOrderCreated,
                bindingSellOrderCreated, bindingAssetDeleted);
    }

    @Bean
    public Jackson2JsonMessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }
}
