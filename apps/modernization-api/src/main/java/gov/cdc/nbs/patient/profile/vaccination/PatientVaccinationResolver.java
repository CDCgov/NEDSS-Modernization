package gov.cdc.nbs.patient.profile.vaccination;

import gov.cdc.nbs.graphql.GraphQLPage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

@Controller
class PatientVaccinationResolver {

    private final int maxPageSize;
    private final PatientVaccinationFinder finder;

    PatientVaccinationResolver(
        @Value("${nbs.max-page-size}") final int maxPageSize,
        final PatientVaccinationFinder finder
    ) {
        this.maxPageSize = maxPageSize;
        this.finder = finder;
    }

    @QueryMapping("findVaccinationsForPatient")
    @PreAuthorize("hasAuthority('FIND-PATIENT') and hasAuthority('VIEW-INTERVENTIONVACCINERECORD')")
    Page<PatientVaccination> find(
        @Argument("patient") final long patient,
        @Argument final GraphQLPage page
    ) {
        Pageable pageable = GraphQLPage.toPageable(page, maxPageSize);
        return this.finder.find(
            patient,
            pageable
        );
    }

}
