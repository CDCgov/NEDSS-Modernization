package gov.cdc.nbs.patient.morbidity;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

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
            List.of("31", "127"),
            List.of()
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
            List.of("29", "113", "229"),
            List.of()
        );

        PatientMorbidityRowMerger merger = new PatientMorbidityRowMerger();

        PatientMorbidity actual = merger.merge(first, second);

        assertThat(actual.treatments()).containsExactlyInAnyOrder("31", "127", "29", "113", "229");
    }

    @Test
    void should_merge_with_values_of_first_and_merged_treatments_of_first_and_second_without_duplicate_treatments() {
        PatientMorbidity first = new PatientMorbidity(
            3L,
            Instant.parse("2023-02-01T16:11:54Z"),
            "provider",
            Instant.parse("2023-01-16T05:11:12Z"),
            "condition",
            "jurisdiction",
            "event",
            null,
            List.of("31", "113"),
            List.of()
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
            List.of("29", "113", "229"),
            List.of()
        );

        PatientMorbidityRowMerger merger = new PatientMorbidityRowMerger();

        PatientMorbidity actual = merger.merge(first, second);

        assertThat(actual.treatments()).containsExactlyInAnyOrder("31", "29", "113", "229");
    }

    @Test
    void should_merge_with_values_of_first_and_merged_lab_order_results_of_first_and_second() {
        PatientMorbidity first = new PatientMorbidity(
            3L,
            Instant.parse("2023-02-01T16:11:54Z"),
            "provider",
            Instant.parse("2023-01-16T05:11:12Z"),
            "condition",
            "jurisdiction",
            "event",
            null,
            List.of(),
            List.of(
                new PatientMorbidity.LabOrderResult(
                    "lab-test",
                    "lab-status",
                    "coded-result",
                    "numeric-result",
                    "text-result"
                )
            )
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
            List.of(),
            List.of(
                new PatientMorbidity.LabOrderResult(
                    "lab-test-other",
                    "lab-status-other",
                    "coded-result-other",
                    "numeric-result-other",
                    "text-result-other"
                )
            )
        );

        PatientMorbidityRowMerger merger = new PatientMorbidityRowMerger();

        PatientMorbidity merged = merger.merge(first, second);

        assertThat(merged.labResults()).satisfiesExactlyInAnyOrder(
            actual -> assertAll(
                () -> assertThat(actual.labTest()).isEqualTo("lab-test"),
                () -> assertThat(actual.status()).isEqualTo("lab-status"),
                () -> assertThat(actual.codedResult()).isEqualTo("coded-result"),
                () -> assertThat(actual.numericResult()).isEqualTo("numeric-result"),
                () -> assertThat(actual.textResult()).isEqualTo("text-result")
            ),
            actual -> assertAll(
                () -> assertThat(actual.labTest()).isEqualTo("lab-test-other"),
                () -> assertThat(actual.status()).isEqualTo("lab-status-other"),
                () -> assertThat(actual.codedResult()).isEqualTo("coded-result-other"),
                () -> assertThat(actual.numericResult()).isEqualTo("numeric-result-other"),
                () -> assertThat(actual.textResult()).isEqualTo("text-result-other")
            )
        );
    }

    @Test
    void should_merge_with_values_of_first_and_merged_lab_order_results_of_first_and_second_without_duplicate_lab_order_results() {
        PatientMorbidity first = new PatientMorbidity(
            3L,
            Instant.parse("2023-02-01T16:11:54Z"),
            "provider",
            Instant.parse("2023-01-16T05:11:12Z"),
            "condition",
            "jurisdiction",
            "event",
            null,
            List.of(),
            List.of(
                new PatientMorbidity.LabOrderResult(
                    "lab-test",
                    "lab-status",
                    "coded-result",
                    "numeric-result",
                    "text-result"
                )
            )
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
            List.of(),
            List.of(
                new PatientMorbidity.LabOrderResult(
                    "lab-test",
                    "lab-status",
                    "coded-result",
                    "numeric-result",
                    "text-result"
                )
            )
        );

        PatientMorbidityRowMerger merger = new PatientMorbidityRowMerger();

        PatientMorbidity merged = merger.merge(first, second);

        assertThat(merged.labResults()).satisfiesExactly(
            actual -> assertAll(
                () -> assertThat(actual.labTest()).isEqualTo("lab-test"),
                () -> assertThat(actual.status()).isEqualTo("lab-status"),
                () -> assertThat(actual.codedResult()).isEqualTo("coded-result"),
                () -> assertThat(actual.numericResult()).isEqualTo("numeric-result"),
                () -> assertThat(actual.textResult()).isEqualTo("text-result")
            )
        );
    }
}
