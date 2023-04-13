package gov.cdc.nbs.patient.profile.general;

import gov.cdc.nbs.patient.profile.PatientProfile;
import gov.cdc.nbs.patient.profile.general.PatientGeneral;
import gov.cdc.nbs.patient.profile.general.PatientGeneralFinder;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
class PatientGeneralResolver {

    private final PatientGeneralFinder finder;

    PatientGeneralResolver(final PatientGeneralFinder finder) {
        this.finder = finder;
    }

    @SchemaMapping("general")
    @PreAuthorize("hasAuthority('FIND-PATIENT')")
    Optional<PatientGeneral> resolve(final PatientProfile profile) {
        return this.finder.find(profile.id());
    }
}
