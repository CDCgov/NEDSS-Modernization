package gov.cdc.nbs.patient.morbidity;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

class PatientMorbidityRowAccumulatorTest {

    @Test
    void should_accumulate_morbidity_instances_with_same_id_into_one_morbidity() {

        PatientMorbidityRowMerger merger = spy(new PatientMorbidityRowMerger());

        PatientMorbidityRowAccumulator accumulator = new PatientMorbidityRowAccumulator(merger);

        Collection<PatientMorbidity> actual = accumulator.accumulate(
                new PatientMorbidity(
                    3L,
                    Instant.parse("2023-02-01T16:11:54Z"),
                    "provider",
                    Instant.parse("2023-01-16T05:11:12Z"),
                    "condition",
                    "jurisdiction",
                    "event",
                    null,
                    List.of(),
                    List.of()
                )
            )
            .accumulate(
                new PatientMorbidity(
                    3L,
                    Instant.parse("2023-02-01T16:11:54Z"),
                    "provider",
                    Instant.parse("2023-01-16T05:11:12Z"),
                    "condition",
                    "jurisdiction",
                    "event",
                    null,
                    List.of(),
                    List.of()
                )
            )
            .accumulated();

        assertThat(actual).hasSize(1);

        verify(merger).merge(any(), any());

    }

    @Test
    void should_accumulate_morbidity_instances_with_differing_id_into_multiple_morbidity() {

        PatientMorbidityRowMerger merger = spy(new PatientMorbidityRowMerger());

        PatientMorbidityRowAccumulator accumulator = new PatientMorbidityRowAccumulator(merger);

        Collection<PatientMorbidity> actual = accumulator.accumulate(
                new PatientMorbidity(
                    3L,
                    Instant.parse("2023-02-01T16:11:54Z"),
                    "provider",
                    Instant.parse("2023-01-16T05:11:12Z"),
                    "condition",
                    "jurisdiction",
                    "event",
                    null,
                    List.of(),
                    List.of()
                )
            )
            .accumulate(
                new PatientMorbidity(
                    463L,
                    Instant.parse("2023-02-01T16:11:54Z"),
                    "provider-other",
                    Instant.parse("2023-01-16T05:11:12Z"),
                    "condition-other",
                    "jurisdiction-other",
                    "event-other",
                    null,
                    List.of(),
                    List.of()
                )
            )
            .accumulated();

        assertThat(actual).hasSize(2);

        verifyNoInteractions(merger);
    }

    @Test
    void should_merge_morbidity_instances_of_two_mergers_with_distinct_values() {


        PatientMorbidityRowMerger merger = spy(new PatientMorbidityRowMerger());

        PatientMorbidityRowAccumulator first = new PatientMorbidityRowAccumulator(merger);
        first.accumulate(
            new PatientMorbidity(
                3L,
                Instant.parse("2023-02-01T16:11:54Z"),
                "provider",
                Instant.parse("2023-01-16T05:11:12Z"),
                "condition",
                "jurisdiction",
                "event",
                null,
                List.of(),
                List.of()
            )
        );

        PatientMorbidityRowAccumulator second = new PatientMorbidityRowAccumulator(merger);
        second.accumulate(
            new PatientMorbidity(
                463L,
                Instant.parse("2023-02-01T16:11:54Z"),
                "provider-other",
                Instant.parse("2023-01-16T05:11:12Z"),
                "condition-other",
                "jurisdiction-other",
                "event-other",
                null,
                List.of(),
                List.of()
            )
        );

        PatientMorbidityRowAccumulator actual = first.merge(second);

        assertThat(actual.accumulated()).satisfiesExactlyInAnyOrder(
            actualMorbidity -> assertThat(actualMorbidity.morbidity()).isEqualTo(3L),
            actualMorbidity -> assertThat(actualMorbidity.morbidity()).isEqualTo(463L)
        );

    }

    @Test
    void should_merge_morbidity_instances_of_two_mergers_with_similar_values() {

        PatientMorbidityRowMerger merger = spy(new PatientMorbidityRowMerger());

        PatientMorbidityRowAccumulator first = new PatientMorbidityRowAccumulator(merger);
        first.accumulate(
            new PatientMorbidity(
                3L,
                Instant.parse("2023-02-01T16:11:54Z"),
                "provider",
                Instant.parse("2023-01-16T05:11:12Z"),
                "condition",
                "jurisdiction",
                "event",
                null,
                List.of("A"),
                List.of(mock(PatientMorbidity.LabOrderResult.class))
            )
        );

        PatientMorbidityRowAccumulator second = new PatientMorbidityRowAccumulator(merger);
        second.accumulate(
            new PatientMorbidity(
                3L,
                Instant.parse("2023-02-01T16:11:54Z"),
                "provider-other",
                Instant.parse("2023-01-16T05:11:12Z"),
                "condition-other",
                "jurisdiction-other",
                "event-other",
                null,
                List.of("B"),
                List.of(mock(PatientMorbidity.LabOrderResult.class))
            )
        );

        List<PatientMorbidity> actual = first.merge(second).accumulated();

        assertThat(actual).satisfiesExactlyInAnyOrder(
            actualMorbidity -> assertAll(
                () -> assertThat(actualMorbidity.morbidity()).isEqualTo(3L),
                () -> assertThat(actualMorbidity.treatments()).contains("A", "B"),
                () -> assertThat(actualMorbidity.labResults()).hasSize(2)
            )
        );

    }
}
