package gov.cdc.nbs.patient.profile.investigation;

import gov.cdc.nbs.graphql.GraphQLPage;
import gov.cdc.nbs.patient.profile.investigation.PatientInvestigationFinder.Criteria;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

@Controller
class PatientInvestigationResolver {

    private final int maxPageSize;
    private final PatientInvestigationFinder finder;

    PatientInvestigationResolver(
        @Value("${nbs.max-page-size}") final int maxPageSize,
        final PatientInvestigationFinder finder
    ) {
        this.maxPageSize = maxPageSize;
        this.finder = finder;
    }

    @QueryMapping("findInvestigationsForPatient")
    @PreAuthorize("hasAuthority('FIND-PATIENT') and hasAuthority('VIEW-INVESTIGATION')")
    Page<PatientInvestigation> find(
        @Argument("patient") final long patient,
        @Argument("openOnly") final Boolean openOnly,
        @Argument final GraphQLPage page
    ) {
        Criteria criteria = resolveCriteria(patient, Boolean.TRUE.equals(openOnly));
        Pageable pageable = GraphQLPage.toPageable(page, maxPageSize);
        return this.finder.find(
            criteria,
            pageable
        );
    }

    private Criteria resolveCriteria(final long patient, final boolean openOnly) {
        return openOnly
            ? new Criteria(patient, Criteria.Status.OPEN)
            : new Criteria(patient);
    }

}
