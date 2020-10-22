package com.henrique.market.event;

import com.henrique.market.domain.Product;
import com.henrique.market.domain.ProductType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductEvent {

    @NotNull
    private String name;

    @NotNull
    private BigDecimal price;

    @NotNull
    @NotBlank
    private String brand;

    @NotNull
    private ProductType productType;

    @NotNull
    private String upc;

    @NotNull
    private Long quantity;

    public static Product toDomain(ProductEvent event) {
        return Product.builder()
            .name(event.getName())
            .price(event.getPrice())
            .brand(event.getBrand())
            .productType(event.productType)
            .upc(event.getUpc())
            .quantity(event.getQuantity())
            .build();
    }

}
