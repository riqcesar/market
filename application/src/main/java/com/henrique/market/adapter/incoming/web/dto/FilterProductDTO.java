package com.henrique.market.adapter.incoming.web.dto;

import com.henrique.market.domain.ProductType;
import com.henrique.market.usecase.FilterProductUseCase;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FilterProductDTO {

    private String name;

    private BigDecimal priceMin;

    private BigDecimal priceMax;

    private String brand;

    private ProductType productType;

    private String upc;

    private Long quantity;

    public static FilterProductUseCase.Specification toSpecification(final FilterProductDTO productDto) {
        return FilterProductUseCase.Specification.builder()
            .name(productDto.getName() != null ? productDto.getName() : null)
            .priceMin(productDto.getPriceMin() != null ? productDto.getPriceMin() : null)
            .priceMax(productDto.getPriceMax() != null ? productDto.getPriceMax() : null)
            .brand(productDto.getBrand() != null ? productDto.getBrand() : null)
            .productType(productDto.getProductType() != null ? productDto.getProductType() : null)
            .upc(productDto.getUpc() != null ? productDto.getUpc() : null)
            .quantity(productDto.getQuantity() != null ? productDto.getQuantity() : null)
            .build();
    }

}
