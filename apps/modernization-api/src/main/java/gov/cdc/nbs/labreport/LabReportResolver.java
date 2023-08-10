package gov.cdc.nbs.labreport;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import gov.cdc.nbs.entity.elasticsearch.LabReport;
import gov.cdc.nbs.graphql.GraphQLPage;

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

}
