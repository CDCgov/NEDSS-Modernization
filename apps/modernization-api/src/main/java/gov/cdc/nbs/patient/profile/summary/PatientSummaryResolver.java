package gov.cdc.nbs.patient.profile.summary;

import gov.cdc.nbs.patient.profile.PatientProfile;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
class PatientSummaryResolver {

    private final PatientSummaryFinder finder;

    PatientSummaryResolver(final PatientSummaryFinder finder) {
        this.finder = finder;
    }

    @SchemaMapping("summary")
    @PreAuthorize("hasAuthority('FIND-PATIENT')")
    Optional<PatientSummary> resolve(final PatientProfile profile) {
        return this.finder.find(profile.id(), profile.asOf());
    }
}
