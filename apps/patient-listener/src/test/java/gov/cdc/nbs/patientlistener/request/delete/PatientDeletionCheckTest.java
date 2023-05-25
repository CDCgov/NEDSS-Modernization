package gov.cdc.nbs.patientlistener.request.delete;

import gov.cdc.nbs.message.patient.event.PatientRequest;
import gov.cdc.nbs.patientlistener.request.UserAuthorizationCheck;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class PatientDeletionCheckTest {

    @Test
    void should_delegate_authorization_check() {
        UserAuthorizationCheck authorizationCheck = mock(UserAuthorizationCheck.class);
        PatientAssociationCheck associationCheck = mock(PatientAssociationCheck.class);

        PatientDeletionCheck check = new PatientDeletionCheck(authorizationCheck, associationCheck);

        PatientRequest.Delete request = new PatientRequest.Delete("key", 823L, 307L);

        check.check(request);

        verify(authorizationCheck).authorized(request, "VIEW-PATIENT", "DELETE-PATIENT");
    }

    @Test
    void should_delegate_association_check() {
        UserAuthorizationCheck authorizationCheck = mock(UserAuthorizationCheck.class);
        PatientAssociationCheck associationCheck = mock(PatientAssociationCheck.class);

        PatientDeletionCheck check = new PatientDeletionCheck(authorizationCheck, associationCheck);

        PatientRequest.Delete request = new PatientRequest.Delete("key", 823L, 307L);

        check.check(request);

        verify(associationCheck).check(request);
    }
}
