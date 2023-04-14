package gov.cdc.nbs.patient.profile.gender;

import gov.cdc.nbs.patient.profile.PatientProfile;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PatientGenderResolverTest {

    @Test
    void should_resolve_gender_for_provided_patient_profile() {
        PatientGenderFinder finder = mock(PatientGenderFinder.class);

        when(finder.find(anyLong()))
            .thenReturn(Optional.of(mock(PatientGender.class)));

        PatientGenderResolver resolver = new PatientGenderResolver(finder);

        PatientProfile profile = mock(PatientProfile.class);

        when(profile.id()).thenReturn(2963L);

        Optional<PatientGender> actual = resolver.resolve(profile);

        assertThat(actual)
            .as("The resolved patient gender comes from the finder")
            .isPresent();

        verify(finder).find(2963L);

    }
}
