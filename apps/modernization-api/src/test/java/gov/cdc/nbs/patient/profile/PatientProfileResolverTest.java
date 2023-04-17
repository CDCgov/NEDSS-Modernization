package gov.cdc.nbs.patient.profile;

import gov.cdc.nbs.patient.identifier.PatientLocalIdentifierResolver;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

class PatientProfileResolverTest {

    @Test
    void should_find_patient_profile_by_identifier() {

        PatientProfile profile = mock(PatientProfile.class);

        PatientProfileFinder finder = mock(PatientProfileFinder.class);

        when(finder.findById(anyLong())).thenReturn(Optional.of(profile));

        PatientLocalIdentifierResolver identifierResolver = mock(PatientLocalIdentifierResolver.class);

        PatientProfileResolver resolver = new PatientProfileResolver(finder, identifierResolver);

        Optional<PatientProfile> actual = resolver.find(2963L, null);

        assertThat(actual).contains(profile);

        verify(finder).findById(2963L);

        verifyNoInteractions(identifierResolver);
    }

    @Test
    void should_find_patient_profile_by_identifier_over_short_identifier() {

        PatientProfile profile = mock(PatientProfile.class);

        PatientProfileFinder finder = mock(PatientProfileFinder.class);

        when(finder.findById(anyLong())).thenReturn(Optional.of(profile));

        PatientLocalIdentifierResolver identifierResolver = mock(PatientLocalIdentifierResolver.class);

        PatientProfileResolver resolver = new PatientProfileResolver(finder, identifierResolver);

        Optional<PatientProfile> actual = resolver.find(2963L, 3259L);

        assertThat(actual).contains(profile);

        verify(finder).findById(2963L);

        verifyNoInteractions(identifierResolver);
    }

    @Test
    void should_find_patient_profile_by_short_id() {

        PatientProfile profile = mock(PatientProfile.class);

        PatientProfileFinder finder = mock(PatientProfileFinder.class);

        when(finder.findByLocalId(any())).thenReturn(Optional.of(profile));

        PatientLocalIdentifierResolver identifierResolver = mock(PatientLocalIdentifierResolver.class);

        when(identifierResolver.resolve(anyLong())).thenReturn("local-identifier");

        PatientProfileResolver resolver = new PatientProfileResolver(finder, identifierResolver);

        Optional<PatientProfile> actual = resolver.find(null, 4271L);

        assertThat(actual).contains(profile);

        verify(identifierResolver).resolve(4271L);
        verify(finder).findByLocalId("local-identifier");
        verifyNoMoreInteractions(finder);

    }

    @Test
    void should_not_find_patient_without_patient_identifier_or_short_identifier() {

        PatientProfileFinder finder = mock(PatientProfileFinder.class);
        PatientLocalIdentifierResolver identifierResolver = mock(PatientLocalIdentifierResolver.class);

        PatientProfileResolver resolver = new PatientProfileResolver(finder, identifierResolver);

        Optional<PatientProfile> actual = resolver.find(null, null);

        assertThat(actual).isNotPresent();
    }
}
