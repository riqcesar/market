package com.henrique.market.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.SubscribableChannel;

@Configuration
public class ProductNotifierKafkaConfig {

    @Bean
    public SubscribableChannel newProductChannel(final ProductNotifierBinding topicBinding) {
        return topicBinding.newProductChannel();
    }

}
