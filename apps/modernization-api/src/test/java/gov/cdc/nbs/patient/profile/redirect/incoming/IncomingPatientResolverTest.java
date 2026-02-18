package gov.cdc.nbs.patient.profile.redirect.incoming;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import jakarta.servlet.http.Cookie;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;

class IncomingPatientResolverTest {

  @Test
  void should_resolve_incoming_patient_from_return_patient_cookie() {
    MockHttpServletRequest request = new MockHttpServletRequest();
    request.setCookies(new Cookie("Return-Patient", "2803"));

    IncomingPatient incoming = mock(IncomingPatient.class);

    ReturningIncomingPatientResolver returning = mock(ReturningIncomingPatientResolver.class);
    when(returning.from(any())).thenReturn(Optional.of(incoming));

    ActionIncomingPatientResolver actions = mock(ActionIncomingPatientResolver.class);

    IncomingPatientResolver resolver = new IncomingPatientResolver(returning, actions);

    Optional<IncomingPatient> found = resolver.from(request);

    assertThat(found).containsSame(incoming);

    verify(returning).from(request);

    verifyNoInteractions(actions);
  }

  @Test
  void should_should_prefer_return_patient_cookie_over_patient_action_cookie() {
    MockHttpServletRequest request = new MockHttpServletRequest();
    request.setCookies(new Cookie("Return-Patient", "2803"));
    request.setCookies(new Cookie("Patient-Action", "5099"));

    IncomingPatient incoming = mock(IncomingPatient.class);

    ReturningIncomingPatientResolver returning = mock(ReturningIncomingPatientResolver.class);
    when(returning.from(any())).thenReturn(Optional.of(incoming));

    ActionIncomingPatientResolver actions = mock(ActionIncomingPatientResolver.class);

    IncomingPatientResolver resolver = new IncomingPatientResolver(returning, actions);

    Optional<IncomingPatient> found = resolver.from(request);

    assertThat(found).containsSame(incoming);

    verify(returning).from(request);

    verifyNoInteractions(actions);
  }

  @Test
  void should_resolve_incoming_patient_from_patient_action_cookie() {
    MockHttpServletRequest request = new MockHttpServletRequest();
    request.setCookies(new Cookie("Patient-Action", "5099"));

    IncomingPatient incoming = mock(IncomingPatient.class);

    ReturningIncomingPatientResolver returning = mock(ReturningIncomingPatientResolver.class);
    when(returning.from(any())).thenReturn(Optional.empty());

    ActionIncomingPatientResolver actions = mock(ActionIncomingPatientResolver.class);
    when(actions.from(any())).thenReturn(Optional.of(incoming));

    IncomingPatientResolver resolver = new IncomingPatientResolver(returning, actions);

    Optional<IncomingPatient> found = resolver.from(request);

    assertThat(found).containsSame(incoming);

    verify(returning).from(request);

    verify(actions).from(request);
  }
}
