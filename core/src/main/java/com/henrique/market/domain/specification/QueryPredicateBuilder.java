package com.henrique.market.domain.specification;

import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * Class comments go here...
 *
 * @author Henrique Cesar
 * @version 1.0 23/06/2020
 */
public class QueryPredicateBuilder {

    private final List<Predicate> predicates = new ArrayList<>();

    public QueryPredicateBuilder addPredicate(final Predicate predicate, final Object object) {
        if (!ObjectUtils.isEmpty(object)) {
            predicates.add(predicate);
        }
        return this;
    }

    /**
     * @return QueryPredicateBuilder
     */
    public Predicate[] build() {
        final Predicate[] predicatesArray = this.predicates.toArray(new Predicate[this.predicates.size()]);
        predicates.clear();
        return predicatesArray;
    }

    /**
     * This method verify a predicate existence
     *
     * @return QueryPredicateBuilder
     */
    public boolean hasPredicate() {
        return !CollectionUtils.isEmpty(predicates);
    }

}
