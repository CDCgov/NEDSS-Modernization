package gov.cdc.nbs.patient.profile.address.change;

import gov.cdc.nbs.id.IdGeneratorService;
import gov.cdc.nbs.patient.PatientException;
import gov.cdc.nbs.patient.RequestContext;
import gov.cdc.nbs.patient.profile.PatientProfileService;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class PatientAddressChangeServiceTest {

  @Test
  void should_throw_patient_not_found_error_when_patient_cannot_be_resolved() {

    IdGeneratorService generator = mock(IdGeneratorService.class);

    PatientProfileService profileService = mock(PatientProfileService.class);

    when(profileService.with(anyLong(), any())).thenReturn(Optional.empty());

    PatientAddressChangeService service =
        new PatientAddressChangeService(generator, profileService);

    NewPatientAddressInput input = mock(NewPatientAddressInput.class);

    when(input.patient()).thenReturn(1021L);

    RequestContext context = new RequestContext(523L, LocalDateTime.now());

    assertThatThrownBy(
        () -> service.add(context, input)
    ).isInstanceOf(PatientException.class)
        .hasMessageContaining("1021");

    verify(profileService).with(eq(1021L), any());

  }

}
