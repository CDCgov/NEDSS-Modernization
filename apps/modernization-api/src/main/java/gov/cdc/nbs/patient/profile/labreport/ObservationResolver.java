package gov.cdc.nbs.patient.profile.labreport;

import gov.cdc.nbs.entity.odse.Observation;
import gov.cdc.nbs.repository.ObservationRepository;

import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import java.util.List;

// follow up observations associated with a lab report/observation
@Controller
class ObservationResolver {
    private final ObservationRepository observationRepository;

    ObservationResolver(final ObservationRepository observationRepository) {
        this.observationRepository = observationRepository;
    }

    @SchemaMapping("observations")
    @PreAuthorize("hasAuthority('FIND-PATIENT')")
    List<Observation> resolve(final long observationUid) {
        return observationRepository.findAllObservationsAssociatedWithAnObservation(observationUid);
    }
}
