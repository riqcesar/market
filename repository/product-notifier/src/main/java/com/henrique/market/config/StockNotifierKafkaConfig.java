package com.henrique.market.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.SubscribableChannel;

@Configuration
public class StockNotifierKafkaConfig {

    @Bean
    public SubscribableChannel newProductChannel(final StockNotifierBinding topicBinding) {
        return topicBinding.newProductChannel();
    }

}
