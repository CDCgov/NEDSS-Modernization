package gov.cdc.nbs.patient.profile.mortality;

import gov.cdc.nbs.patient.profile.PatientProfile;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PatientMortalityResolverTest {

    @Test
    void should_resolve_mortality_for_provided_patient_profile() {
        PatientMortalityFinder finder = mock(PatientMortalityFinder.class);

        when(finder.find(anyLong()))
            .thenReturn(Optional.of(mock(PatientMortality.class)));

        PatientMortalityResolver resolver = new PatientMortalityResolver(finder);

        PatientProfile profile = mock(PatientProfile.class);

        when(profile.id()).thenReturn(2963L);

        Optional<PatientMortality> actual = resolver.resolve(profile);

        assertThat(actual)
            .as("The resolved patient mortality comes from the finder")
            .isPresent();

        verify(finder).find(2963L);

    }
}
