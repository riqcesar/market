package com.henrique.market.port;

import com.henrique.market.domain.Product;
import com.henrique.market.usecase.FilterProductUseCase;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public interface ProductPort {

    Product save(Product product);

    Optional<Product> findByUpc(String upc);

    List<Product> filter(FilterProductUseCase.Specification specification, Sort sort);//FIXME tirar sort do core

}
