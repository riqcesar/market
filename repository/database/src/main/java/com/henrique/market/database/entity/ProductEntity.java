package com.henrique.market.database.entity;

import com.henrique.market.domain.Product;
import com.henrique.market.domain.ProductType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "PRODUCT")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    @Column(name = "NAME")
    private String name;

    @NotNull
    @Column(name = "PRICE")
    private BigDecimal price;

    @NotEmpty
    @Column(name = "BRAND")
    private String brand;

    @Column(name = "TYPE")
    @Enumerated(EnumType.STRING)
    private ProductType productType;

    @NotEmpty
    @Column(name = "UPC")
    private String upc;

    @NotNull
    @Column(name = "QUANTITY")
    private Long quantity;

    public static ProductEntity from(final Product product) {
        return ProductEntity.builder()
            .id(product.getId())
            .name(product.getName())
            .price(product.getPrice())
            .brand(product.getBrand())
            .productType(product.getProductType())
            .upc(product.getUpc())
            .quantity(product.getQuantity())
            .build();
    }

    public Product toDomain() {
        return Product.builder()
            .id(id)
            .name(name)
            .price(price)
            .brand(brand)
            .productType(productType)
            .upc(upc)
            .quantity(quantity)
            .build();
    }

}
