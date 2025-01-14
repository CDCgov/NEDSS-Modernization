package gov.cdc.nbs.patient.treatment;

import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.graphql.GraphQLPage;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PatientTreatmentByPatientResolverTest {

  @Test
  void should_find_treatments_for_provided_patient_using_the_finder() {
    PatientTreatmentFinder finder = mock(PatientTreatmentFinder.class);

    when(finder.find(anyLong()))
        .thenReturn(List.of(mock(PatientTreatment.class)));

    PatientTreatmentByPatientResolver resolver = new PatientTreatmentByPatientResolver(25, finder);

    Person patient = new Person(2963L, "local");

    List<PatientTreatment> actual = resolver.resolve(patient);

    assertThat(actual)
        .as("The resolved list of patients comes from the finder")
        .hasSize(1);

    verify(finder).find(2963L);

  }

  @Test
  void should_find_paginated_treatments_for_the_provided_patient_using_the_finder() {

    PatientTreatmentFinder finder = mock(PatientTreatmentFinder.class);

    when(finder.find(anyLong(), any()))
        .thenAnswer(args -> new PageImpl<>(
                List.of(mock(PatientTreatment.class)),
                args.getArgument(1, Pageable.class),
                1L
            )
        );

    PatientTreatmentByPatientResolver resolver = new PatientTreatmentByPatientResolver(25, finder);

    GraphQLPage page = new GraphQLPage(25, 2);

    Page<PatientTreatment> actual = resolver.find(1861L, page);

    assertThat(actual)
        .as("The resolved paginated list of patients comes from the finder")
        .hasSize(1);

    ArgumentCaptor<Pageable> captor = ArgumentCaptor.forClass(Pageable.class);

    verify(finder).find(eq(1861L), captor.capture());

    Pageable actualPageable = captor.getValue();

    assertThat(actualPageable.getPageNumber()).isEqualTo(2);
    assertThat(actualPageable.getPageSize()).isEqualTo(25);
  }
}
