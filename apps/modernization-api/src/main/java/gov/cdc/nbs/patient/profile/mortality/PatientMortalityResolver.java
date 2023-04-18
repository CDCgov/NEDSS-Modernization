package gov.cdc.nbs.patient.profile.mortality;

import gov.cdc.nbs.patient.profile.PatientProfile;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
class PatientMortalityResolver {

    private final PatientMortalityFinder finder;

    PatientMortalityResolver(final PatientMortalityFinder finder) {
        this.finder = finder;
    }

    @SchemaMapping("mortality")
    @PreAuthorize("hasAuthority('FIND-PATIENT')")
    Optional<PatientMortality> resolve(final PatientProfile profile) {
        return this.finder.find(profile.id());
    }
}
