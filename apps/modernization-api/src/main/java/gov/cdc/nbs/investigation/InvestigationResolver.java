package gov.cdc.nbs.investigation;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import gov.cdc.nbs.entity.elasticsearch.Investigation;
import gov.cdc.nbs.graphql.GraphQLPage;
import gov.cdc.nbs.graphql.filter.InvestigationFilter;

@Controller
public class InvestigationResolver {
    private final Integer maxPageSize;
    private final InvestigationFinder investigationFinder;

    public InvestigationResolver(
            @Value("${nbs.max-page-size: 50}") Integer maxPageSize,
            InvestigationFinder investigationFinder) {
        this.maxPageSize = maxPageSize;
        this.investigationFinder = investigationFinder;
    }

    @QueryMapping
    @PreAuthorize("hasAuthority('FIND-PATIENT') and hasAuthority('VIEW-INVESTIGATION')")
    public Page<Investigation> findInvestigationsByFilter(@Argument InvestigationFilter filter,
            @Argument GraphQLPage page) {
        return investigationFinder.find(filter, GraphQLPage.toPageable(page, maxPageSize));
    }

    @QueryMapping
    @PreAuthorize("hasAuthority('VIEWWORKUP-PATIENT') and hasAuthority('VIEW-INVESTIGATION')")
    public Page<Investigation> findOpenInvestigationsForPatient(@Argument Long patientId, @Argument GraphQLPage page) {
        return investigationFinder.find(patientId,
                GraphQLPage.toPageable(page, maxPageSize));
    }
}
