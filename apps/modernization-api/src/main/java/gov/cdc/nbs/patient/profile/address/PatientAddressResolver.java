package gov.cdc.nbs.patient.profile.address;

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
class PatientAddressResolver {

    private final int maxPageSize;
    private final PatientAddressFinder finder;

    PatientAddressResolver(
        @Value("${nbs.max-page-size}") final int maxPageSize,
        final PatientAddressFinder finder
    ) {
        this.finder = finder;
        this.maxPageSize = maxPageSize;
    }

    @SchemaMapping("addresses")
    @PreAuthorize("hasAuthority('FIND-PATIENT')")
    Page<PatientAddress> resolve(
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
