package gov.cdc.nbs.patient.profile.general;

import gov.cdc.nbs.patient.profile.PatientProfile;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PatientGeneralResolverTest {
    @Test
    void should_resolve_general_for_provided_patient_profile() {
        PatientGeneralFinder finder = mock(PatientGeneralFinder.class);

        when(finder.find(anyLong()))
            .thenReturn(Optional.of(mock(PatientGeneral.class)));

        PatientGeneralResolver resolver = new PatientGeneralResolver(finder);

        PatientProfile profile = mock(PatientProfile.class);

        when(profile.id()).thenReturn(2963L);

        Optional<PatientGeneral> actual = resolver.resolve(profile);

        assertThat(actual)
            .as("The resolved patient general comes from the finder")
            .isPresent();

        verify(finder).find(2963L);

    }
}
