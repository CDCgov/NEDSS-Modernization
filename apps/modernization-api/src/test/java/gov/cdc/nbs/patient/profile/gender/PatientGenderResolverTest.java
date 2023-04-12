package gov.cdc.nbs.patient.profile.gender;

import gov.cdc.nbs.graphql.GraphQLPage;
import gov.cdc.nbs.patient.profile.PatientProfile;
import gov.cdc.nbs.patient.profile.gender.PatientGender;
import gov.cdc.nbs.patient.profile.gender.PatientGenderFinder;
import gov.cdc.nbs.patient.profile.gender.PatientGenderResolver;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
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
        when(profile.asOf()).thenReturn(Instant.parse("2023-04-01T00:01:02Z"));

        Optional<PatientGender> actual = resolver.resolve(profile);

        assertThat(actual)
            .as("The resolved patient gender comes from the finder")
            .isPresent();

        verify(finder).find(2963L);

    }
}
