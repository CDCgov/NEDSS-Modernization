package gov.cdc.nbs.patient.profile.summary;

import java.util.List;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import gov.cdc.nbs.patient.profile.summary.PatientSummary.PatientSummaryIdentification;

@Controller
class PatientSummaryIdentificationResolver {

    private PatientSummaryIdentificationFinder finder;

    PatientSummaryIdentificationResolver(final PatientSummaryIdentificationFinder finder) {
        this.finder = finder;
    }

    @SchemaMapping("identification")
    @PreAuthorize("hasAuthority('FIND-PATIENT')")
    List<PatientSummaryIdentification> resolve(
            final PatientSummary summary) {
        return this.finder.find(
                summary.patient());
    }
}
