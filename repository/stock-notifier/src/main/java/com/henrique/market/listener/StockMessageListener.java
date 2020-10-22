package com.henrique.market.listener;

import com.henrique.market.config.StockNotifierBinding;
import com.henrique.market.event.NewProductsEvent;
import com.henrique.market.event.ProductEvent;
import com.henrique.market.usecase.CreateProductUseCase;
import lombok.AllArgsConstructor;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

/**
 * @author Henrique Cesar
 * @version 1.0 18/09/2020
 */
@Component
@AllArgsConstructor
public class StockMessageListener {

    private final CreateProductUseCase createProductUseCase;

    @StreamListener(value = StockNotifierBinding.STOCK_NEW_PRODUCT)
    public void receive(Message<NewProductsEvent> message) {
        var newProductEvent = message.getPayload();
        newProductEvent.getProductsEvent().stream().forEach((e) -> createProductUseCase.execute(ProductEvent.toDomain(e)));
    }

}
