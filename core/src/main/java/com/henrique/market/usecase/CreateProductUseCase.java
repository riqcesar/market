package com.henrique.market.usecase;

import com.henrique.market.domain.Product;
import com.henrique.market.port.ProductPort;
import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
public class CreateProductUseCase {

    private final ProductPort productPort;

    public Product execute(Product newProduct) {
        final Optional<Product> productFound = productPort.findByUpc(newProduct.getUpc());

        if (productFound.isEmpty()) {
            return productPort.save(newProduct);
        }

        Product product = productFound.get();
        product.addQuantity(newProduct.getQuantity());

        return productPort.save(product);
    }

}
