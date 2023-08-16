package gov.cdc.nbs.patient.profile.labreport;

import gov.cdc.nbs.repository.ObservationRepository;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import java.util.List;
import gov.cdc.nbs.entity.projections.LabReport2;

@Controller
class LabReport2Resolver {
    private final ObservationRepository observationRepository;

    LabReport2Resolver(final ObservationRepository observationRepository) {
        this.observationRepository = observationRepository;
    }

    @QueryMapping(name = "findAllLabReportsByPersonUid")
    @PreAuthorize("hasAuthority('FIND-PATIENT')")
    List<LabReport2> resolve(final long personUid) {
        return observationRepository.findAllLabReportsByPersonUid(personUid);
    }
}
