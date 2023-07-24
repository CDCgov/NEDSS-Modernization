package gov.cdc.nbs.patient.profile.investigation;

import gov.cdc.nbs.graphql.GraphQLPage;
import gov.cdc.nbs.patient.profile.investigation.PatientInvestigationFinder.Criteria;
import gov.cdc.nbs.service.SecurityService;
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
    private final SecurityService securityService;

    PatientInvestigationResolver(
            @Value("${nbs.max-page-size}") final int maxPageSize,
            final PatientInvestigationFinder finder,
            final SecurityService securityService) {
        this.maxPageSize = maxPageSize;
        this.finder = finder;
        this.securityService = securityService;
    }

    @QueryMapping("findInvestigationsForPatient")
    @PreAuthorize("hasAuthority('FIND-PATIENT') and hasAuthority('VIEW-INVESTIGATION')")
    Page<PatientInvestigation> find(
            @Argument("patient") final long patient,
            @Argument("openOnly") final Boolean openOnly,
            @Argument final GraphQLPage page) {
        Criteria criteria = resolveCriteria(patient, Boolean.TRUE.equals(openOnly));
        Pageable pageable = GraphQLPage.toPageable(page, maxPageSize);
        return this.finder.find(
                criteria,
                pageable);
    }

    private Criteria resolveCriteria(final long patient, final boolean openOnly) {
        // Get the current user and a list of their available program area + jurisdiction OIDs
        var userOids = securityService.getCurrentUserProgramAreaJurisdictionOids();
        return openOnly
                ? new Criteria(patient, userOids, Criteria.Status.OPEN)
                : new Criteria(patient, userOids);
    }

}
