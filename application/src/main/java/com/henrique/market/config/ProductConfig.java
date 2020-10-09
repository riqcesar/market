package com.henrique.market.config;

import com.henrique.market.port.ProductPort;
import com.henrique.market.usecase.CreateProductUseCase;
import com.henrique.market.usecase.FilterProductUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProductConfig {

    @Bean
    public FilterProductUseCase findAllProductUseCase(ProductPort productPort) {

        return new FilterProductUseCase(productPort);
    }

    @Bean
    public CreateProductUseCase createProductUseCase(ProductPort productPort) {
        return new CreateProductUseCase(productPort);
    }

}
