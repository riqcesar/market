package com.henrique.market.listener;

import com.henrique.market.config.ProductNotifierBinding;
import com.henrique.market.event.NewProductEvent;
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
public class NewProductMessageListener {

    private final CreateProductUseCase createProductUseCase;

    @StreamListener(value = ProductNotifierBinding.MARKET_NEW_PRODUCT)
    public void receive(Message<NewProductEvent> message){
        var newProductEvent = message.getPayload();
        createProductUseCase.execute(NewProductEvent.toDomain(newProductEvent));
    }

}
