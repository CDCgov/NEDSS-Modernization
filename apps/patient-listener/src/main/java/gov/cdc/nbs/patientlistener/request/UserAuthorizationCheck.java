package gov.cdc.nbs.patientlistener.request;

import gov.cdc.nbs.authentication.UserAuthorizationVerifier;
import gov.cdc.nbs.message.patient.event.PatientRequest;
import org.springframework.stereotype.Component;

@Component
public class UserAuthorizationCheck {

    private final UserAuthorizationVerifier verifier;

    public UserAuthorizationCheck(final UserAuthorizationVerifier verifier) {
        this.verifier = verifier;
    }

    public void authorized(final PatientRequest request, final String...permissions) {
        if (!verifier.isAuthorized(request.userId(), permissions)) {
            throw new UserNotAuthorizedException(request.requestId());
        }
    }
}
