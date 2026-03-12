package gov.cdc.nbs.patient.profile.redirect.incoming;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

class ModernizedPatientProfileRedirectResolverTest {

  @Test
  void should_redirect_to_fallback_when_patient_not_resolvable_from_query_parameter() {

    IncomingPatientResolver incoming = mock(IncomingPatientResolver.class);
    RequestedIncomingPatientResolver requested = mock(RequestedIncomingPatientResolver.class);

    when(requested.from(any())).thenReturn(Optional.empty());

    MockHttpServletRequest request = new MockHttpServletRequest();

    ModernizedPatientProfileRedirectResolver resolver =
        new ModernizedPatientProfileRedirectResolver(incoming, requested);

    ResponseEntity<Void> response = resolver.fromPatientParameters(request);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SEE_OTHER);
    assertThat(response.getHeaders().getLocation()).hasPath("/search");
  }

  @Test
  void should_redirect_to_fallback_when_patient_not_resolvable_from_cookie() {

    IncomingPatientResolver incoming = mock(IncomingPatientResolver.class);
    RequestedIncomingPatientResolver requested = mock(RequestedIncomingPatientResolver.class);

    when(incoming.from(any())).thenReturn(Optional.empty());

    MockHttpServletRequest request = new MockHttpServletRequest();

    ModernizedPatientProfileRedirectResolver resolver =
        new ModernizedPatientProfileRedirectResolver(incoming, requested);

    ResponseEntity<Void> response = resolver.fromReturnPatient(request);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SEE_OTHER);
    assertThat(response.getHeaders().getLocation()).hasPath("/search");
  }
}
