package com.henrique.market.config;


import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface ProductNotifierBinding {

    String MARKET_NEW_PRODUCT = "market-new-product";

    @Input(MARKET_NEW_PRODUCT)
    SubscribableChannel newProductChannel();

}
