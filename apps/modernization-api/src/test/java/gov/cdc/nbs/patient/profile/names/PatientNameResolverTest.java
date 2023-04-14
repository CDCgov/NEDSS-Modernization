package gov.cdc.nbs.patient.profile.names;

import gov.cdc.nbs.graphql.GraphQLPage;
import gov.cdc.nbs.patient.profile.PatientProfile;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PatientNameResolverTest {

    @Test
    void should_resolve_name_for_provided_patient_profile() {
        PatientNameFinder finder = mock(PatientNameFinder.class);

        when(finder.find(anyLong(), any()))
            .thenReturn(new PageImpl<>(List.of(mock(PatientName.class))));

        PatientNameResolver resolver = new PatientNameResolver(10, finder);

        PatientProfile profile = mock(PatientProfile.class);

        when(profile.id()).thenReturn(2963L);

        GraphQLPage page = new GraphQLPage(5, 2);

        Page<PatientName> actual = resolver.resolve(profile, page);

        assertThat(actual)
            .as("The resolved patient name comes from the finder")
            .hasSize(1);

        ArgumentCaptor<Pageable> captor = ArgumentCaptor.forClass(Pageable.class);

        verify(finder).find(eq(2963L), captor.capture());

        Pageable actual_pageable = captor.getValue();

        assertThat(actual_pageable.getPageNumber()).isEqualTo(2);
        assertThat(actual_pageable.getPageSize()).isEqualTo(5);
    }

    @Test
    void should_resolve_name_for_provided_patient_profile_without_page() {
        PatientNameFinder finder = mock(PatientNameFinder.class);

        when(finder.find(anyLong(), any()))
            .thenReturn(new PageImpl<>(List.of(mock(PatientName.class))));

        PatientNameResolver resolver = new PatientNameResolver(10, finder);

        PatientProfile profile = mock(PatientProfile.class);

        when(profile.id()).thenReturn(2963L);

        GraphQLPage page = null;

        Page<PatientName> actual = resolver.resolve(profile, page);

        assertThat(actual)
            .as("The resolved patient name comes from the finder")
            .hasSize(1);

        ArgumentCaptor<Pageable> captor = ArgumentCaptor.forClass(Pageable.class);

        verify(finder).find(eq(2963L), captor.capture());

        Pageable actual_pageable = captor.getValue();

        assertThat(actual_pageable.getPageNumber()).isZero();
        assertThat(actual_pageable.getPageSize()).isEqualTo(10);
    }
}
