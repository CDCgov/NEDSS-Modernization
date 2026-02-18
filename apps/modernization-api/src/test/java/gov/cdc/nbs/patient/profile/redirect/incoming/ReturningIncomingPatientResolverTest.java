package gov.cdc.nbs.patient.profile.redirect.incoming;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import jakarta.servlet.http.Cookie;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;

class ReturningIncomingPatientResolverTest {

  @Test
  void should_use_identifier_from_cookie_to_find_the_patient_identifier() {

    MockHttpServletRequest request = new MockHttpServletRequest();
    request.setCookies(new Cookie("Return-Patient", "2803"));

    IncomingPatientFinder finder = mock(IncomingPatientFinder.class);

    IncomingPatient incoming = mock(IncomingPatient.class);
    when(finder.find(anyLong())).thenReturn(Optional.of(incoming));

    ReturningIncomingPatientResolver resolver = new ReturningIncomingPatientResolver(finder);

    Optional<IncomingPatient> found = resolver.from(request);

    assertThat(found).containsSame(incoming);

    verify(finder).find(2803L);
  }

  @Test
  void should_return_empty_returning_patient_cookie_is_not_present() {

    MockHttpServletRequest request = new MockHttpServletRequest();

    IncomingPatientFinder finder = mock(IncomingPatientFinder.class);
    when(finder.find(anyLong())).thenReturn(Optional.empty());

    ReturningIncomingPatientResolver resolver = new ReturningIncomingPatientResolver(finder);

    Optional<IncomingPatient> found = resolver.from(request);

    assertThat(found).isNotPresent();
    verifyNoInteractions(finder);
  }

  @Test
  void should_return_empty_returning_patient_cookie_value_is_not_a_number() {

    MockHttpServletRequest request = new MockHttpServletRequest();
    request.setCookies(new Cookie("Return-Patient", "other-value"));

    IncomingPatientFinder finder = mock(IncomingPatientFinder.class);

    when(finder.find(anyLong())).thenReturn(Optional.empty());

    ReturningIncomingPatientResolver resolver = new ReturningIncomingPatientResolver(finder);

    Optional<IncomingPatient> found = resolver.from(request);

    assertThat(found).isNotPresent();

    verifyNoInteractions(finder);
  }
}
