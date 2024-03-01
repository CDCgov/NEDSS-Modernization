package gov.cdc.nbs.patient.profile.labreport;

import gov.cdc.nbs.repository.ObservationRepository;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.data.domain.Page;
import gov.cdc.nbs.entity.projections.PatientLabReport;
import gov.cdc.nbs.graphql.GraphQLPage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.graphql.data.method.annotation.Argument;

@Controller
class PatientLabReportResolver {
    private final ObservationRepository observationRepository;
    private final Integer maxPageSize;

    PatientLabReportResolver(@Value("${nbs.max-page-size: 10}") final Integer maxPageSize,
            final ObservationRepository observationRepository) {
        this.maxPageSize = maxPageSize;
        this.observationRepository = observationRepository;
    }

    @QueryMapping(name = "findLabReportsForPatient")
    @PreAuthorize("hasAuthority('FIND-PATIENT')")
    Page<PatientLabReport> resolve(@Argument("personUid") final long personUid, @Argument GraphQLPage page) {
        return observationRepository.findLabReportsForPatient(personUid, GraphQLPage.toPageable(page, maxPageSize));
    }

    @QueryMapping(name = "findLabReportsForPatientCount")
    @PreAuthorize("hasAuthority('FIND-PATIENT')")
    long resolve(@Argument("personUid") final long personUid) {
        return observationRepository.findLabReportsForPatientCount(personUid);
    }
}
