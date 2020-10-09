package com.henrique.market.usecase;

import com.henrique.market.domain.Product;
import com.henrique.market.port.ProductPort;
import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
public class CreateProductUseCase {

    private final ProductPort productPort;

    public Product execute(Product product) {
        final Optional<Product> productFound = productPort.findByUpc(product.getUpc());
        productFound.ifPresentOrElse(p -> p.addQuantity(product.getQuantity()), () -> productPort.create(product));
        return productFound.orElse(product);
    }

}
