package gov.cdc.nbs.patient.profile.identification.change;

import gov.cdc.nbs.patient.PatientException;
import gov.cdc.nbs.patient.RequestContext;
import gov.cdc.nbs.patient.profile.PatientProfileService;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class PatientIdentificationChangeServiceTest {

  @Test
  void should_throw_patient_not_found_error_when_patient_cannot_be_resolved() {

    PatientProfileService profileService = mock(PatientProfileService.class);

    when(profileService.with(anyLong(), any())).thenReturn(Optional.empty());

    PatientIdentificationChangeService service =
        new PatientIdentificationChangeService(profileService);

    NewPatientIdentificationInput input = new NewPatientIdentificationInput(
        1021L,
        Instant.parse("2023-06-01T03:21:00Z"),
        "identification-type-value",
        "authority-value",
        "identification-value"
    );
    RequestContext context = new RequestContext(523L, Instant.now());

    assertThatThrownBy(
        () -> service.add(context, input)
    ).isInstanceOf(PatientException.class)
        .hasMessageContaining("1021");

    verify(profileService).with(eq(1021L), any());

  }
}
