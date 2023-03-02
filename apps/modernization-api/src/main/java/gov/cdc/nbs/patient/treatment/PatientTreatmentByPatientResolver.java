package gov.cdc.nbs.patient.treatment;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
class PatientTreatmentByPatientResolver {

    private final PatientTreatmentFinder finder;

    PatientTreatmentByPatientResolver(final PatientTreatmentFinder finder) {
        this.finder = finder;
    }

    @QueryMapping(name = "findTreatmentsForPatient")
    @PreAuthorize("hasAuthority('FIND-PATIENT') and hasAuthority('VIEW-TREATMENT')")
    List<PatientTreatment> find(@Argument("patient") final long patient) {
        return this.finder.find(patient);
    }

}
