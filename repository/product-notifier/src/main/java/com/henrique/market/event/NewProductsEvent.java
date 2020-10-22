package com.henrique.market.event;

import com.henrique.market.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NewProductsEvent {

    private List<ProductEvent> productsEvent;

    public static List<Product> toDomain(List<ProductEvent> productsEvent) {
        return productsEvent.stream().map(ProductEvent::toDomain).collect(Collectors.toList());
    }

}
