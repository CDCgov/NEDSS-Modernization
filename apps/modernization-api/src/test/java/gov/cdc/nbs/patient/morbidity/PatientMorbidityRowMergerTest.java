package gov.cdc.nbs.patient.morbidity;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PatientMorbidityRowMergerTest {

    @Test
    void should_merge_with_values_of_first_and_merged_treatments_of_first_and_second() {
        PatientMorbidity first = new PatientMorbidity(
            3L,
            Instant.parse("2023-02-01T16:11:54Z"),
            "provider",
            Instant.parse("2023-01-16T05:11:12Z"),
            "condition",
            "jurisdiction",
            "event",
            null,
            List.of("31", "127")
        );

        PatientMorbidity second = new PatientMorbidity(
            3L,
            Instant.parse("2023-02-01T16:11:54Z"),
            "provider",
            Instant.parse("2023-01-16T05:11:12Z"),
            "condition",
            "jurisdiction",
            "event",
            null,
            List.of("29", "113", "229")
        );

        PatientMorbidityRowMerger merger = new PatientMorbidityRowMerger();

        PatientMorbidity actual = merger.merge(first, second);

        assertThat(actual.treatments()).containsExactlyInAnyOrder("31", "127", "29", "113", "229");
    }
}
