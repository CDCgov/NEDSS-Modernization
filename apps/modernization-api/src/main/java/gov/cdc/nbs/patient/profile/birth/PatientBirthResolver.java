package gov.cdc.nbs.patient.profile.birth;

import gov.cdc.nbs.patient.profile.PatientProfile;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
class PatientBirthResolver {

    private final PatientBirthFinder finder;

    PatientBirthResolver(final PatientBirthFinder finder) {
        this.finder = finder;
    }

    @SchemaMapping("birth")
    @PreAuthorize("hasAuthority('FIND-PATIENT')")
    Optional<PatientBirth> resolve(final PatientProfile profile) {
        return this.finder.find(profile.id());
    }
}
