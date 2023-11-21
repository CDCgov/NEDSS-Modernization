package gov.cdc.nbs.patient.profile.labreport;

import gov.cdc.nbs.entity.odse.Observation;
import gov.cdc.nbs.repository.ObservationRepository;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import java.util.List;

// follow up observations associated with a lab report/observation
@Controller
class ObservationsResolver {
    private final ObservationRepository observationRepository;

    ObservationsResolver(final ObservationRepository observationRepository) {
        this.observationRepository = observationRepository;
    }

    @SchemaMapping(typeName = "LabReport2", field = "observations2")
    @PreAuthorize("hasAuthority('FIND-PATIENT')")
    List<Observation> resolve(LabReport2 labreport) {
        return observationRepository.findAllObservationsAssociatedWithAnObservation(labreport.observationUid());
    }
}
