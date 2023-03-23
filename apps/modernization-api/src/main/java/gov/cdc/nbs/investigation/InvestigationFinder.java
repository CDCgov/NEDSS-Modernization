package gov.cdc.nbs.investigation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import gov.cdc.nbs.entity.elasticsearch.Investigation;
import gov.cdc.nbs.graphql.filter.InvestigationFilter;
import gov.cdc.nbs.graphql.filter.InvestigationFilter.InvestigationStatus;

@Component
public class InvestigationFinder {
    private final ElasticsearchOperations operations;
    private final InvestigationQueryBuilder queryBuilder;

    public InvestigationFinder(
            ElasticsearchOperations operations,
            InvestigationQueryBuilder queryBuilder) {
        this.operations = operations;
        this.queryBuilder = queryBuilder;
    }

    @PreAuthorize("hasAuthority('FIND-PATIENT') and hasAuthority('VIEW-INVESTIGATION')")
    public Page<Investigation> find(InvestigationFilter filter, Pageable pageable) {
        var query = queryBuilder.buildInvestigationQuery(filter, pageable);
        return performSearch(query, Investigation.class);
    }

    @PreAuthorize("hasAuthority('VIEWWORKUP-PATIENT') and hasAuthority('VIEW-INVESTIGATION')")
    public Page<Investigation> find(Long patientId, Pageable pageable) {
        var filter = new InvestigationFilter();
        filter.setPatientId(patientId);
        filter.setInvestigationStatus(InvestigationStatus.OPEN);
        var query = queryBuilder.buildInvestigationQuery(filter, pageable);
        return performSearch(query, Investigation.class);
    }

    private <T> Page<T> performSearch(NativeSearchQuery query, Class<T> clazz) {
        var hits = operations.search(query, clazz);
        var list = hits.getSearchHits().stream().map(SearchHit::getContent).toList();
        return new PageImpl<>(list, query.getPageable(), hits.getTotalHits());
    }
}
