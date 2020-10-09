package com.henrique.market.usecase;

import com.henrique.market.domain.Product;
import com.henrique.market.domain.ProductType;
import com.henrique.market.port.ProductPort;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@AllArgsConstructor
public class FilterProductUseCase {

    private final ProductPort productPort;

    public List<Product> execute(final Specification specification, final Sort sort) {
        return Collections.unmodifiableList(productPort.filter(specification, sort));
    }

    @Getter
    @AllArgsConstructor
    @Builder
    public static class Specification {

        private String name;
        private BigDecimal priceMin;
        private BigDecimal priceMax;
        private String brand;
        private ProductType productType;
        private String upc;
        private Long quantity;

    }

}
