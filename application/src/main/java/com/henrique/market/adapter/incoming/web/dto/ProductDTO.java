package com.henrique.market.adapter.incoming.web.dto;

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
public class ProductDTO {

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


    public static Product toDomain(final ProductDTO productDto) {
        return Product.builder()
            .name(productDto.getName())
            .price(productDto.getPrice())
            .brand(productDto.getBrand())
            .productType(productDto.getProductType())
            .upc(productDto.getUpc())
            .quantity(productDto.getQuantity())
            .build();
    }

}
