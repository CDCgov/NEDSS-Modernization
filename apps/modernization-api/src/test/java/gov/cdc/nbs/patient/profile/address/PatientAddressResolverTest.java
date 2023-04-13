package gov.cdc.nbs.patient.profile.address;

import gov.cdc.nbs.graphql.GraphQLPage;
import gov.cdc.nbs.patient.profile.PatientProfile;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PatientAddressResolverTest {

    @Test
    void should_resolve_address_for_provided_patient_profile() {
        PatientAddressFinder finder = mock(PatientAddressFinder.class);

        when(finder.find(anyLong(), any()))
            .thenReturn(new PageImpl<>(List.of(mock(PatientAddress.class))));

        PatientAddressResolver resolver = new PatientAddressResolver(10, finder);

        PatientProfile profile = mock(PatientProfile.class);

        when(profile.id()).thenReturn(2963L);
        when(profile.asOf()).thenReturn(Instant.parse("2023-04-01T00:01:02Z"));

        GraphQLPage page = new GraphQLPage(5, 2);

        Page<PatientAddress> actual = resolver.resolve(profile, page);

        assertThat(actual)
            .as("The resolved patient addresses comes from the finder")
            .hasSize(1);

        ArgumentCaptor<Pageable> captor = ArgumentCaptor.forClass(Pageable.class);

        verify(finder).find(eq(2963L), captor.capture());

        Pageable actual_pageable = captor.getValue();

        assertThat(actual_pageable.getPageNumber()).isEqualTo(2);
        assertThat(actual_pageable.getPageSize()).isEqualTo(5);
    }

    @Test
    void should_resolve_address_for_provided_patient_profile_without_page() {
        PatientAddressFinder finder = mock(PatientAddressFinder.class);

        when(finder.find(anyLong(), any()))
            .thenReturn(new PageImpl<>(List.of(mock(PatientAddress.class))));

        PatientAddressResolver resolver = new PatientAddressResolver(10, finder);

        PatientProfile profile = mock(PatientProfile.class);

        when(profile.id()).thenReturn(2963L);
        when(profile.asOf()).thenReturn(Instant.parse("2023-04-01T00:01:02Z"));

        Page<PatientAddress> actual = resolver.resolve(profile, null);

        assertThat(actual)
            .as("The resolved patient addresses comes from the finder")
            .hasSize(1);

        ArgumentCaptor<Pageable> captor = ArgumentCaptor.forClass(Pageable.class);

        verify(finder).find(eq(2963L), captor.capture());

        Pageable actual_pageable = captor.getValue();

        assertThat(actual_pageable.getPageNumber()).isZero();
        assertThat(actual_pageable.getPageSize()).isEqualTo(10);
    }
}
