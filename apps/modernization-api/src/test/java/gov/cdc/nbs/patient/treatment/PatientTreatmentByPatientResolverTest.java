package gov.cdc.nbs.patient.treatment;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class PatientTreatmentByPatientResolverTest {

    @Test
    void should_find_treatments_for_the_provided_patient_using_the_finder() {

        PatientTreatmentFinder finder = mock(PatientTreatmentFinder.class);

        when(finder.find(anyLong()))
                .thenReturn(
                        List.of(
                                new PatientTreatment(
                                        2119L,
                                        Instant.parse("2022-05-13T10:34:00Z"),
                                        "provider-value",
                                        Instant.parse("2022-05-10T15:04:39Z"),
                                        "description-value",
                                        "event-value",
                                        new PatientTreatment.Investigation(
                                                2251L,
                                                "local-value",
                                                "condition-value"
                                        )

                                )
                        )
                );

        PatientTreatmentByPatientResolver resolver = new PatientTreatmentByPatientResolver(finder);

        List<PatientTreatment> actual = resolver.find(1861L);

        assertThat(actual)
                .as("The resolved list of patients comes from the finder")
                .hasSize(1);

        verify(finder).find(1861L);
    }
}