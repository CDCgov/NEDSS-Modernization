package gov.cdc.nbs.patient.profile.labreport;

import gov.cdc.nbs.repository.ObservationRepository;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import gov.cdc.nbs.entity.projections.PatientLabReport;
import gov.cdc.nbs.entity.projections.Observation2;
import java.util.List;

// follow up observations associated with a lab report/observation
@Controller
class ObservationsResolver {
    private final ObservationRepository observationRepository;

    ObservationsResolver(final ObservationRepository observationRepository) {
        this.observationRepository = observationRepository;
    }

    @SchemaMapping(typeName = "PatientLabReport", field = "observations2")
    @PreAuthorize("hasAuthority('FIND-PATIENT')")
    List<Observation2> resolve(PatientLabReport labreport) {
        return observationRepository.findAllObservationsAssociatedWithAnObservation(labreport.getObservationUid());
    }
}
