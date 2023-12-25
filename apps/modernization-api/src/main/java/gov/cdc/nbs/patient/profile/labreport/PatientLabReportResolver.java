package gov.cdc.nbs.patient.profile.labreport;

import gov.cdc.nbs.repository.ObservationRepository;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import java.util.List;
import gov.cdc.nbs.entity.projections.PatientLabReport;
import gov.cdc.nbs.graphql.GraphQLPage;

import org.springframework.graphql.data.method.annotation.Argument;

@Controller
class PatientLabReportResolver {
    private final ObservationRepository observationRepository;

    PatientLabReportResolver(final ObservationRepository observationRepository) {
        this.observationRepository = observationRepository;
    }

    @QueryMapping(name = "findLabReportsForPatient")
    @PreAuthorize("hasAuthority('FIND-PATIENT')")
    List<PatientLabReport> resolve(@Argument("personUid") final long personUid, @Argument GraphQLPage page) {
        return observationRepository.findLabReportsForPatient(personUid, page.getPageNumber(), page.getPageSize());
    }

    @QueryMapping(name = "findLabReportsForPatientCount")
    @PreAuthorize("hasAuthority('FIND-PATIENT')")
    long resolve(@Argument("personUid") final long personUid) {
        return observationRepository.findLabReportsForPatientCount(personUid);
    }
}
