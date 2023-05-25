package gov.cdc.nbs.patient.delete;

import gov.cdc.nbs.model.PatientEventResponse;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

@Controller
class PatientDeleteController {

    private final PatientDeletionRequester requester;

    PatientDeleteController(final PatientDeletionRequester requester) {
        this.requester = requester;
    }

    @MutationMapping("deletePatient")
    @PreAuthorize("hasAuthority('VIEW-PATIENT') and hasAuthority('DELETE-PATIENT')")
    public PatientEventResponse delete(@Argument("patientId") long patient) {
        return requester.request(patient);
    }
}
