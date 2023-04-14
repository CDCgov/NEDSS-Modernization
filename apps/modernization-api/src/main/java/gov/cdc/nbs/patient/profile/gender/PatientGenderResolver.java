package gov.cdc.nbs.patient.profile.gender;

import gov.cdc.nbs.patient.profile.PatientProfile;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
class PatientGenderResolver {

    private final PatientGenderFinder finder;

    PatientGenderResolver(final PatientGenderFinder finder) {
        this.finder = finder;
    }

    @SchemaMapping("gender")
    @PreAuthorize("hasAuthority('FIND-PATIENT')")
    Optional<PatientGender> resolve(final PatientProfile profile) {
        return this.finder.find(profile.id());
    }
}
