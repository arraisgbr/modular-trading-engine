package com.usp.mac0499.catalog.infrastructure.amqp;

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

        var transactionCompletedQueue = new Queue("catalog.transactionCompleted.queue");

        var transactionCompletedExchange = new TopicExchange(MessageBrokerConstants.TRANSACTION_COMPLETED_EXCHANGE);

        var bindingTransactionCompleted = BindingBuilder.bind(transactionCompletedQueue).to(transactionCompletedExchange).with("#");

        return new Declarables(transactionCompletedQueue, transactionCompletedQueue, bindingTransactionCompleted);
    }

    @Bean
    public Jackson2JsonMessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }
}
