package gov.cdc.nbs.patient.contact;

import gov.cdc.nbs.graphql.GraphQLPage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

@Controller
class PatientNamedByContactResolver {
    private final int maxPageSize;
    private final PatientNamedByContactFinder finder;

    PatientNamedByContactResolver(
        @Value("${nbs.max-page-size}") final int maxPageSize,
        final PatientNamedByContactFinder finder
    ) {
        this.maxPageSize = maxPageSize;
        this.finder = finder;
    }

    @QueryMapping(name = "findPatientNamedByContact")
    @PreAuthorize("hasAuthority('FIND-PATIENT') and hasAuthority('VIEW-INVESTIGATION')")
    Page<PatientContacts.NamedByContact> find(
        @Argument("patient") final long patient,
        @Argument final GraphQLPage page
    ) {
        Pageable pageable = GraphQLPage.toPageable(page, maxPageSize);
        return finder.find(patient, pageable);
    }

}
