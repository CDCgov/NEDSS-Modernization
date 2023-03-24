package gov.cdc.nbs.labreport;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import gov.cdc.nbs.entity.elasticsearch.LabReport;
import gov.cdc.nbs.graphql.GraphQLPage;
import gov.cdc.nbs.graphql.filter.LabReportFilter;

@Controller
public class LabReportResolver {
    private final LabReportFinder finder;
    private final Integer maxPageSize;


    public LabReportResolver(
            @Value("${nbs.max-page-size: 50}") Integer maxPageSize,
            LabReportFinder finder) {
        this.finder = finder;
        this.maxPageSize = maxPageSize;
    }

    @QueryMapping
    @PreAuthorize("hasAuthority('FIND-PATIENT') and hasAuthority('VIEW-OBSERVATIONLABREPORT')")
    public Page<LabReport> findLabReportsByFilter(@Argument LabReportFilter filter,
            @Argument GraphQLPage page) {

        return finder.find(filter, GraphQLPage.toPageable(page, maxPageSize));
    }

    /**
     * Return a list of lab reports that the user has access to, are associated with a particular patient, and have the
     * status of "UNPROCESSED"
     *
     * This currently only returns lab reports as those are the only documents ingested in elasticsearch, but in the
     * future will also include morbidity reports and case reports
     */
    @QueryMapping
    @PreAuthorize("hasAuthority('VIEWWORKUP-PATIENT') and hasAuthority('VIEW-OBSERVATIONLABREPORT')")
    public Page<LabReport> findDocumentsRequiringReviewForPatient(@Argument Long patientId,
            @Argument GraphQLPage page) {
        return finder.findUnprocessedDocumentsForPatient(patientId, GraphQLPage.toPageable(page, maxPageSize));
    }
}
