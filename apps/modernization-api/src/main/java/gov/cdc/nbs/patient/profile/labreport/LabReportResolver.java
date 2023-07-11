package gov.cdc.nbs.patient.profile.labreport;

import gov.cdc.nbs.entity.odse.Observation;
import gov.cdc.nbs.repository.ObservationRepository;

import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import java.util.Optional;

@Controller
class LabReportResolver {
    private final ObservationRepository observationRepository;

    LabReportResolver(final ObservationRepository observationRepository) {
        this.observationRepository = observationRepository;
    }

    @SchemaMapping("LabReport")
    @PreAuthorize("hasAuthority('FIND-PATIENT')")
    Optional<Observation> resolve(final long observationUid) {
        return observationRepository.findById(observationUid);
    }
}
