package gov.cdc.nbs.patient.profile;

import gov.cdc.nbs.patient.identifier.PatientLocalIdentifierResolver;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
class PatientProfileResolver {

    private final PatientProfileFinder finder;
    private final PatientLocalIdentifierResolver localIdentifierResolver;

    PatientProfileResolver(
        final PatientProfileFinder finder,
        final PatientLocalIdentifierResolver localIdentifierResolver
    ) {
        this.finder = finder;
        this.localIdentifierResolver = localIdentifierResolver;
    }

    @QueryMapping("findPatientProfile")
    @PreAuthorize("hasAuthority('FIND-PATIENT')")
    Optional<PatientProfile> find(
        @Argument("patient") final Long patient,
        @Argument("shortId") final Long shortId
    ) {

        if (patient != null) {
            return this.finder.findById(patient);
        } else if (shortId != null) {
            return findByShortId(shortId);
        }

        return Optional.empty();
    }


    private Optional<PatientProfile> findByShortId(final long shortId) {
        String local = this.localIdentifierResolver.resolve(shortId);

        return this.finder.findByLocalId(local);
    }

}
