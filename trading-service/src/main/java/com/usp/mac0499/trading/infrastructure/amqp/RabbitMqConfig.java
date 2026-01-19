package com.usp.mac0499.trading.infrastructure.amqp;

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

        var removeOrdersInvolvingAssetQueue = new Queue("trading.removeOrdersInvolvingAsset.queue");
        var removeOrdersInvolvingPortfolioQueue = new Queue("trading.removeOrdersInvolvingPortfolio.queue");

        var removeOrdersInvolvingAssetExchange = new TopicExchange(MessageBrokerConstants.ASSET_DELETED_EXCHANGE);
        var removeOrdersInvolvingPortfolioExchange = new TopicExchange(MessageBrokerConstants.PORTFOLIO_DELETED_EXCHANGE);

        var bindingRemoveOrdersInvolvingAsset = BindingBuilder.bind(removeOrdersInvolvingAssetQueue).to(removeOrdersInvolvingAssetExchange).with("#");
        var bindingRemoveOrdersInvolvingPortfolio = BindingBuilder.bind(removeOrdersInvolvingPortfolioQueue).to(removeOrdersInvolvingPortfolioExchange).with("#");

        return new Declarables(removeOrdersInvolvingAssetQueue, removeOrdersInvolvingPortfolioQueue,
                removeOrdersInvolvingAssetExchange, removeOrdersInvolvingPortfolioExchange,
                bindingRemoveOrdersInvolvingAsset, bindingRemoveOrdersInvolvingPortfolio);
    }

    @Bean
    public Jackson2JsonMessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }
}
