package com.henrique.market.adapter.incoming.web;

import com.henrique.market.adapter.incoming.web.dto.FilterProductDTO;
import com.henrique.market.adapter.incoming.web.dto.ProductDTO;
import com.henrique.market.domain.Product;
import com.henrique.market.usecase.CreateProductUseCase;
import com.henrique.market.usecase.FilterProductUseCase;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.data.domain.Sort.Direction.ASC;

@RestController
@RequestMapping("/product")
@AllArgsConstructor
public class ProductController {

    private final CreateProductUseCase createProductUseCase;

    private final FilterProductUseCase filterProductUseCase;

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Product create(@RequestBody @Valid ProductDTO productDto) {
        return createProductUseCase.execute(ProductDTO.toDomain(productDto));
    }

    @GetMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Product> filter(FilterProductDTO productDTO, @SortDefault(value = {"name"}, direction = ASC) final Sort sort) {
        return filterProductUseCase.execute(FilterProductDTO.toSpecification(productDTO), sort);
    }

}
