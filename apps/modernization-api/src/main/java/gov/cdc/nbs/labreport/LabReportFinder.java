package gov.cdc.nbs.labreport;

import java.util.Arrays;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import gov.cdc.nbs.entity.elasticsearch.LabReport;
import gov.cdc.nbs.labreport.LabReportFilter.ProcessingStatus;


@Component
public class LabReportFinder {
    private final ElasticsearchOperations operations;
    private final LabReportQueryBuilder queryBuilder;


    public LabReportFinder(
            ElasticsearchOperations operations,
            LabReportQueryBuilder queryBuilder) {
        this.operations = operations;
        this.queryBuilder = queryBuilder;
    }

    @PreAuthorize("hasAuthority('FIND-PATIENT') and hasAuthority('VIEW-OBSERVATIONLABREPORT')")
    public Page<LabReport> find(LabReportFilter filter, Pageable pageable) {
        var query = queryBuilder.buildLabReportQuery(filter, pageable);
        return performSearch(query, LabReport.class);
    }

    @PreAuthorize("hasAuthority('VIEWWORKUP-PATIENT') and hasAuthority('VIEW-OBSERVATIONLABREPORT')")
    public Page<LabReport> findUnprocessedDocumentsForPatient(Long patientId, Pageable pageable) {
        var filter = new LabReportFilter();
        filter.setPatientId(patientId);
        filter.setProcessingStatus(Arrays.asList(ProcessingStatus.UNPROCESSED));
        var query = queryBuilder.buildLabReportQuery(filter, pageable);
        return performSearch(query, LabReport.class);
    }

    private <T> Page<T> performSearch(NativeSearchQuery query, Class<T> clazz) {
        var hits = operations.search(query, clazz);
        var list = hits.getSearchHits().stream().map(SearchHit::getContent).toList();
        return new PageImpl<>(list, query.getPageable(), hits.getTotalHits());
    }
}
