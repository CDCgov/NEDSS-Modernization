package gov.cdc.nbs.patient.profile.ethnicity;

import gov.cdc.nbs.patient.profile.PatientProfile;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
class PatientEthnicityResolver {
    private final PatientEthnicityFinder finder;

    PatientEthnicityResolver(final PatientEthnicityFinder finder) {
        this.finder = finder;
    }

    @SchemaMapping("ethnicity")
    @PreAuthorize("hasAuthority('FIND-PATIENT')")
    Optional<PatientEthnicity> resolve(final PatientProfile profile) {
        return this.finder.find(profile.id());
    }
}
