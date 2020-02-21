package com.bycnit.socle.repository.specifications;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.bycnit.socle.domain.User;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserSpecification implements Specification<User> {

    /** serialVersionUID */
    private static final long serialVersionUID = 1L;

    private Specification<User> specification;

    /**
     * Constructor
     * 
     * @param specification
     *            prebuilt specs
     */
    public UserSpecification(final Specification<User> specification) {
        this.specification = specification;
    }

    @Override
    public Predicate toPredicate(final Root<User> root, final CriteriaQuery<?> query, final CriteriaBuilder builder) {

        if (specification != null) {
            return builder.and(specification.toPredicate(root, query, builder));
        }

        return null;
    }
}