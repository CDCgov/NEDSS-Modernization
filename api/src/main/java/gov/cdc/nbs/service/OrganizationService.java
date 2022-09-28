package gov.cdc.nbs.service;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import gov.cdc.nbs.entity.Organization;
import gov.cdc.nbs.entity.QOrganization;
import gov.cdc.nbs.graphql.GraphQLPage;
import gov.cdc.nbs.graphql.OrganizationFilter;
import gov.cdc.nbs.repository.OrganizationRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
@AllArgsConstructor
public class OrganizationService {
    private final int MAX_PAGE_SIZE = 50;

    @PersistenceContext
    private final EntityManager entityManager;
    private final OrganizationRepository organizationRepository;

    public Optional<Organization> findOrganizationById(Long id) {
        return organizationRepository.findById(id);
    }

    public Page<Organization> findAllOrganizations(GraphQLPage page) {
        if (page == null) {
            page = new GraphQLPage(MAX_PAGE_SIZE, 0);
        }
        var pageable = PageRequest.of(page.getPageNumber(), Math.min(page.getPageSize(), MAX_PAGE_SIZE));
        return organizationRepository.findAll(pageable);
    }

    public List<Organization> findOrganizationsByFilter(OrganizationFilter filter) {
        // limit page size
        if (filter.getPage().getPageSize() == 0 || filter.getPage().getPageSize() > MAX_PAGE_SIZE) {
            filter.getPage().setPageSize(MAX_PAGE_SIZE);
        }

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

        return query.limit(filter.getPage().getPageSize())
                .offset(filter.getPage().getOffset()).fetch();

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
