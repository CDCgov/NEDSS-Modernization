package gov.cdc.nbs.service;

import java.util.Optional;
import java.util.function.Function;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import com.blazebit.persistence.CriteriaBuilderFactory;
import com.blazebit.persistence.querydsl.BlazeJPAQuery;
import com.querydsl.core.types.dsl.BooleanExpression;

import gov.cdc.nbs.entity.odse.Organization;
import gov.cdc.nbs.entity.odse.QOrganization;
import gov.cdc.nbs.graphql.GraphQLPage;
import gov.cdc.nbs.graphql.filter.OrganizationFilter;
import gov.cdc.nbs.repository.OrganizationRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrganizationService {
    @Value("${nbs.max-page-size: 50}")
    private Integer maxPageSize;

    @PersistenceContext
    private final EntityManager entityManager;
    private final OrganizationRepository organizationRepository;
    private final CriteriaBuilderFactory criteriaBuilderFactory;

    public Optional<Organization> findOrganizationById(Long id) {
        return organizationRepository.findById(id);
    }

    public Page<Organization> findAllOrganizations(GraphQLPage page) {
        var pageable = GraphQLPage.toPageable(page, maxPageSize);
        return organizationRepository.findAll(pageable);
    }

    public Page<Organization> findOrganizationsByFilter(OrganizationFilter filter, GraphQLPage page) {
        // limit page size
        var pageable = GraphQLPage.toPageable(page, maxPageSize);

        var organization = QOrganization.organization;
        var query = new BlazeJPAQuery<Organization>(entityManager, criteriaBuilderFactory);

        query = applyIfFilterNotNull(query, organization.id::eq, filter.getId());
        query = applyIfFilterNotNull(query, organization.displayNm::likeIgnoreCase, filter.getDisplayNm());
        query = applyIfFilterNotNull(query, organization.streetAddr1::likeIgnoreCase, filter.getStreetAddr1());
        query = applyIfFilterNotNull(query, organization.streetAddr2::likeIgnoreCase, filter.getStreetAddr2());
        query = applyIfFilterNotNull(query, organization.cityDescTxt::likeIgnoreCase, filter.getCityDescTxt());
        query = applyIfFilterNotNull(query, organization.cityCd::eq, filter.getCityCd());
        query = applyIfFilterNotNull(query, organization.stateCd::eq, filter.getStateCd());
        query = applyIfFilterNotNull(query, organization.zipCd::eq, filter.getZipCd());

        var results = query.fetchPage((int) pageable.getOffset(),
                pageable.getPageSize());
        return new PageImpl<>(results, pageable, results.getMaxResults());
    }

    private <T> BlazeJPAQuery<Organization> applyIfFilterNotNull(BlazeJPAQuery<Organization> query,
            Function<T, BooleanExpression> expression, T parameter) {
        if (parameter != null) {
            return query.where(expression.apply(parameter));
        } else {
            return query;
        }
    }
}
