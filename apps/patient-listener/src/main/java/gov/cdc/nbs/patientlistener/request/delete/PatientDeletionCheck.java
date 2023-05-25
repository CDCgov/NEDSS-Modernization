package gov.cdc.nbs.patientlistener.request.delete;

import gov.cdc.nbs.message.patient.event.PatientRequest;
import gov.cdc.nbs.patientlistener.request.UserAuthorizationCheck;
import org.springframework.stereotype.Component;

@Component
class PatientDeletionCheck {

    private final UserAuthorizationCheck authorized;
    private final PatientAssociationCheck associated;

    PatientDeletionCheck(
        final UserAuthorizationCheck authorized,
        final PatientAssociationCheck associated
    ) {
        this.authorized = authorized;
        this.associated = associated;
    }

    void check(final PatientRequest.Delete request) {
        authorized.authorized(request, "VIEW-PATIENT", "DELETE-PATIENT");
        associated.check(request);
    }
}
