package com.henrique.market.config;


import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface StockNotifierBinding {

    String STOCK_NEW_PRODUCT = "stock-new-product";

    @Input(STOCK_NEW_PRODUCT)
    SubscribableChannel newProductChannel();

}
