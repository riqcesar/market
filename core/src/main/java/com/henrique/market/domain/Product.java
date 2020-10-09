package com.henrique.market.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;


@Getter
@Builder
@AllArgsConstructor
public class Product {

    private Integer id;

    private String name;

    private BigDecimal price;

    private String brand;

    private ProductType productType;

    private String upc;

    private Long quantity;

    public void addQuantity(final Long quantityToSum) {
        this.quantity = this.quantity + quantityToSum;
    }

}
