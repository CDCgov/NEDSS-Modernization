package gov.cdc.nbs.service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import gov.cdc.nbs.entity.odse.Organization;
import gov.cdc.nbs.entity.odse.QOrganization;
import gov.cdc.nbs.graphql.GraphQLPage;
import gov.cdc.nbs.graphql.searchFilter.OrganizationFilter;
import gov.cdc.nbs.repository.OrganizationRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrganizationService {
    @Value("${nbs.max-page-size: 50}")
    private Integer MAX_PAGE_SIZE;

    @PersistenceContext
    private final EntityManager entityManager;
    private final OrganizationRepository organizationRepository;

    public Optional<Organization> findOrganizationById(Long id) {
        return organizationRepository.findById(id);
    }

    public Page<Organization> findAllOrganizations(GraphQLPage page) {
        var pageable = GraphQLPage.toPageable(page, MAX_PAGE_SIZE);
        return organizationRepository.findAll(pageable);
    }

    public List<Organization> findOrganizationsByFilter(OrganizationFilter filter, GraphQLPage page) {
        // limit page size
        var pageable = GraphQLPage.toPageable(page, MAX_PAGE_SIZE);

        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);

        var organization = QOrganization.organization;
        var query = queryFactory.selectFrom(organization);

        query = applyIfFilterNotNull(query, organization.id::eq, filter.getId());
        query = applyIfFilterNotNull(query, organization.displayNm::likeIgnoreCase, filter.getDisplayNm());
        query = applyIfFilterNotNull(query, organization.streetAddr1::likeIgnoreCase, filter.getStreetAddr1());
        query = applyIfFilterNotNull(query, organization.streetAddr2::likeIgnoreCase, filter.getStreetAddr2());
        query = applyIfFilterNotNull(query, organization.cityDescTxt::likeIgnoreCase, filter.getCityDescTxt());
        query = applyIfFilterNotNull(query, organization.cityCd::eq, filter.getCityCd());
        query = applyIfFilterNotNull(query, organization.stateCd::eq, filter.getStateCd());
        query = applyIfFilterNotNull(query, organization.zipCd::eq, filter.getZipCd());

        return query.limit(pageable.getPageSize()).offset(pageable.getOffset()).fetch();
    }

    private <T> JPAQuery<Organization> applyIfFilterNotNull(JPAQuery<Organization> query,
            Function<T, BooleanExpression> expression, T parameter) {
        if (parameter != null) {
            return query.where(expression.apply(parameter));
        } else {
            return query;
        }
    }
}
