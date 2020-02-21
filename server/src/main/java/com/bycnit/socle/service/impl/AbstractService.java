package com.bycnit.socle.service.impl;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.bycnit.socle.dto.SearchCriteria;
import com.bycnit.socle.repository.specifications.GenericSpecification;

/**
 * Abstract Service which contains all the generic methods
 *
 * @author S.BENDRIOUE
 */
public abstract class AbstractService<T> {

    /**
     * @see com.bycnit.socle.service.impl.AbstractService#toSpecifications(List, Specification)
     */
    protected Specification<T> toSpecifications(final List<SearchCriteria> criterias) {
        return toSpecifications(criterias, null);
    }

    /**
     * Converts the criterias to specifications
     *
     * @param criterias
     *            list of criterias
     * @return an instance of specification
     */
    protected Specification<T> toSpecifications(final List<SearchCriteria> criterias, final Specification<T> initialSpecifications) {
        Specification<T> specifications = initialSpecifications;

        for (final SearchCriteria criteria : criterias) {
            final GenericSpecification<T> userSpecification = new GenericSpecification<>(criteria);

            if (specifications == null) {
                specifications = Specification.where(userSpecification);
            } else {
                specifications = specifications.and(userSpecification);
            }
        }

        return specifications;
    }
}
