package gov.cdc.nbs.patient.profile.phone;

import gov.cdc.nbs.graphql.GraphQLPage;
import gov.cdc.nbs.patient.profile.PatientProfile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

@Controller
class PatientPhoneResolver {

    private final int maxPageSize;
    private final PatientPhoneFinder finder;

    PatientPhoneResolver(
        @Value("${nbs.max-page-size}") final int maxPageSize,
        final PatientPhoneFinder finder
    ) {
        this.finder = finder;
        this.maxPageSize = maxPageSize;
    }

    @SchemaMapping("phones")
    @PreAuthorize("hasAuthority('FIND-PATIENT')")
    Page<PatientPhone> resolve(
        final PatientProfile profile,
        @Argument final GraphQLPage page
    ) {
        Pageable pageable = GraphQLPage.toPageable(page, maxPageSize);
        return this.finder.find(
            profile.id(),
            pageable
        );
    }
}
