package com.henrique.market.database.port;

import com.henrique.market.database.entity.ProductEntity;
import com.henrique.market.database.repository.ProductRepository;
import com.henrique.market.domain.Product;
import com.henrique.market.domain.specification.QueryPredicateBuilder;
import com.henrique.market.port.ProductPort;
import com.henrique.market.usecase.FilterProductUseCase;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class ProductPortImpl implements ProductPort {

    private final ProductRepository productRepository;

    @Override
    public Product save(final Product product) {
        return productRepository.save(ProductEntity.from(product)).toDomain();
    }

    @Override
    public Optional<Product> findByUpc(final String upc) {
        return productRepository.findByUpc(upc).map(ProductEntity::toDomain);
    }

    @Override
    public List<Product> filter(final FilterProductUseCase.Specification specification, final Sort sort) {
        final QueryPredicateBuilder predicateBuilder = new QueryPredicateBuilder();

        final List<ProductEntity> entities = productRepository.findAll((root, query, criteriaBuilder) -> {
            var namePredicate = criteriaBuilder.like(root.get("name"), "%" + nullCheck(specification.getName()) + "%");

            var priceMinPredicate = criteriaBuilder.greaterThanOrEqualTo(root.get("price"), (BigDecimal) nullCheck(specification.getPriceMin()));

            var priceMaxPredicate = criteriaBuilder.lessThanOrEqualTo(root.get("price"), (BigDecimal) nullCheck(specification.getPriceMax()));

            var brandPredicate = criteriaBuilder.like(root.get("brand"), "%" + nullCheck(specification.getBrand()) + "%");

            var productTypePredicate = criteriaBuilder.equal(root.get("productType"), nullCheck(specification.getProductType()));

            var upcPredicate = criteriaBuilder.like(root.get("upc"), "%" + nullCheck(specification.getUpc()) + "%");

            var quantityPredicate = criteriaBuilder.greaterThanOrEqualTo(root.get("quantity"), (Long) nullCheck(specification.getQuantity()));

            predicateBuilder.addPredicate(namePredicate, specification.getName());
            predicateBuilder.addPredicate(priceMinPredicate, specification.getPriceMin());
            predicateBuilder.addPredicate(priceMaxPredicate, specification.getPriceMax());
            predicateBuilder.addPredicate(brandPredicate, specification.getBrand());
            predicateBuilder.addPredicate(productTypePredicate, specification.getProductType());
            predicateBuilder.addPredicate(upcPredicate, specification.getUpc());
            predicateBuilder.addPredicate(quantityPredicate, specification.getQuantity());

            return predicateBuilder.hasPredicate() ? criteriaBuilder.and(predicateBuilder.build()) : null;
        });

        return entities.stream().map(ProductEntity::toDomain).collect(Collectors.toList());
    }

    private Object nullCheck(Object obj) {
        return ObjectUtils.isNotEmpty(obj) ? obj : null;
    }

}
