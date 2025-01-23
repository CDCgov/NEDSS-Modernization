package gov.cdc.nbs.patient.profile.names.change;

import gov.cdc.nbs.patient.PatientException;
import gov.cdc.nbs.patient.RequestContext;
import gov.cdc.nbs.patient.demographic.name.SoundexResolver;
import gov.cdc.nbs.patient.profile.PatientProfileService;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class PatientNameChangeServiceTest {

  @Test
  void should_throw_patient_not_found_error_when_patient_cannot_be_resolved() {

    PatientProfileService profileService = mock(PatientProfileService.class);

    when(profileService.with(anyLong(), any())).thenReturn(Optional.empty());

    SoundexResolver encoder = mock(SoundexResolver.class);

    PatientNameChangeService service =
        new PatientNameChangeService(profileService, encoder);

    NewPatientNameInput input = mock(NewPatientNameInput.class);

    when(input.patient()).thenReturn(1021L);

    RequestContext context = new RequestContext(523L, Instant.now());

    assertThatThrownBy(
        () -> service.add(context, input)
    ).isInstanceOf(PatientException.class)
        .hasMessageContaining("1021");

    verify(profileService).with(eq(1021L), any());

  }

}
