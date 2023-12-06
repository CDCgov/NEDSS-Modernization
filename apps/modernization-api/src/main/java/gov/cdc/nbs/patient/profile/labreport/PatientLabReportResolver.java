package gov.cdc.nbs.patient.profile.labreport;

import gov.cdc.nbs.repository.ObservationRepository;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import java.util.List;
import gov.cdc.nbs.entity.projections.PatientLabReport;
import org.springframework.graphql.data.method.annotation.Argument;

@Controller
class PatientLabReportResolver {
    private final ObservationRepository observationRepository;

    PatientLabReportResolver(final ObservationRepository observationRepository) {
        this.observationRepository = observationRepository;
    }

    @QueryMapping(name = "findAllLabReportsByPersonUid")
    @PreAuthorize("hasAuthority('FIND-PATIENT')")
    List<PatientLabReport> resolve(@Argument("personUid") final long personUid) {
        return observationRepository.findAllLabReportsByPersonUid(personUid);
    }
}
