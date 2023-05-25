package gov.cdc.nbs.patientlistener.request;

import gov.cdc.nbs.authentication.UserAuthorizationVerifier;
import gov.cdc.nbs.message.patient.event.PatientRequest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserAuthorizationCheckTest {

    @Test
    void should_allow_authorized_permissions() {
        UserAuthorizationVerifier verifier = mock(UserAuthorizationVerifier.class);

        when(verifier.isAuthorized(321L, "AUTHORIZED")).thenReturn(true);

        UserAuthorizationCheck check = new UserAuthorizationCheck(verifier);

        PatientRequest.Delete request = new PatientRequest.Delete("key", 123L, 321L);

        assertThatNoException().isThrownBy(() -> check.authorized(request, "AUTHORIZED"));
    }

    @Test
    void should_not_allow_unauthorized_permissions() {
        UserAuthorizationVerifier verifier = mock(UserAuthorizationVerifier.class);

        when(verifier.isAuthorized(eq(321L), anyString())).thenReturn(false);

        UserAuthorizationCheck check = new UserAuthorizationCheck(verifier);

        PatientRequest.Delete request = new PatientRequest.Delete("key", 123L, 321L);

        assertThatThrownBy(() -> check.authorized(request, "ANY"))
            .isInstanceOf(UserNotAuthorizedException.class);

    }
}
