package gov.cdc.nbs.patient.profile.birth;

import gov.cdc.nbs.patient.profile.PatientProfile;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PatientBirthResolverTest {

    @Test
    void should_resolve_birth_for_provided_patient_profile() {
        PatientBirthFinder finder = mock(PatientBirthFinder.class);

        when(finder.find(anyLong()))
            .thenReturn(Optional.of(mock(PatientBirth.class)));

        PatientBirthResolver resolver = new PatientBirthResolver(finder);

        PatientProfile profile = mock(PatientProfile.class);

        when(profile.id()).thenReturn(2963L);
        when(profile.asOf()).thenReturn(Instant.parse("2023-04-01T00:01:02Z"));

        Optional<PatientBirth> actual = resolver.resolve(profile);

        assertThat(actual)
            .as("The resolved patient birth comes from the finder")
            .isPresent();

        verify(finder).find(2963L);

    }
}
