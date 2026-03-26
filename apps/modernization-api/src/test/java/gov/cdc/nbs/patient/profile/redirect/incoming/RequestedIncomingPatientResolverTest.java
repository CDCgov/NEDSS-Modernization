package gov.cdc.nbs.patient.profile.redirect.incoming;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;

class RequestedIncomingPatientResolverTest {

  @Test
  void should_return_master_patient_record_identifier_from_query_parameter() {

    MockHttpServletRequest request = new MockHttpServletRequest();
    request.addParameter("MPRUid", "2803");

    IncomingPatient incoming = mock(IncomingPatient.class);

    IncomingPatientFinder finder = mock(IncomingPatientFinder.class);
    when(finder.find(anyLong())).thenReturn(Optional.of(incoming));

    RequestedIncomingPatientResolver resolver = new RequestedIncomingPatientResolver(finder);

    Optional<IncomingPatient> found = resolver.from(request);

    assertThat(found).containsSame(incoming);

    verify(finder).find(2803L);
  }

  @Test
  void should_return_patient_identifier_from_query_parameter() {

    MockHttpServletRequest request = new MockHttpServletRequest();
    request.addParameter("uid", "2069");

    IncomingPatient incoming = mock(IncomingPatient.class);

    IncomingPatientFinder finder = mock(IncomingPatientFinder.class);
    when(finder.find(anyLong())).thenReturn(Optional.of(incoming));

    RequestedIncomingPatientResolver resolver = new RequestedIncomingPatientResolver(finder);

    Optional<IncomingPatient> found = resolver.from(request);

    assertThat(found).containsSame(incoming);

    verify(finder).find(2069L);
  }

  @Test
  void
      should_prefer_master_patient_record_identifier_over_patient_identifier_from_query_parameter() {

    MockHttpServletRequest request = new MockHttpServletRequest();
    request.addParameter("MPRUid", "2803");
    request.addParameter("uid", "2069");

    IncomingPatient incoming = mock(IncomingPatient.class);

    IncomingPatientFinder finder = mock(IncomingPatientFinder.class);
    when(finder.find(anyLong())).thenReturn(Optional.of(incoming));

    RequestedIncomingPatientResolver resolver = new RequestedIncomingPatientResolver(finder);

    Optional<IncomingPatient> found = resolver.from(request);

    assertThat(found).containsSame(incoming);

    verify(finder).find(2803L);
  }

  @Test
  void should_return_empty_when_master_patient_record_identifier_is_not_a_number() {

    MockHttpServletRequest request = new MockHttpServletRequest();
    request.addParameter("MPRUid", "other-value");

    IncomingPatientFinder finder = mock(IncomingPatientFinder.class);
    when(finder.find(anyLong())).thenReturn(Optional.empty());

    RequestedIncomingPatientResolver resolver = new RequestedIncomingPatientResolver(finder);

    Optional<IncomingPatient> found = resolver.from(request);

    assertThat(found).isNotPresent();

    verifyNoInteractions(finder);
  }

  @Test
  void should_return_empty_when_patient_identifier_is_not_a_number() {

    MockHttpServletRequest request = new MockHttpServletRequest();
    request.addParameter("uid", "other-value");

    IncomingPatientFinder finder = mock(IncomingPatientFinder.class);
    when(finder.find(anyLong())).thenReturn(Optional.empty());

    RequestedIncomingPatientResolver resolver = new RequestedIncomingPatientResolver(finder);

    Optional<IncomingPatient> found = resolver.from(request);

    assertThat(found).isNotPresent();

    verifyNoInteractions(finder);
  }
}
