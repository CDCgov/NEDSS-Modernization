package gov.cdc.nbs.patient.morbidity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Accumulates multiple {@code PatientMorbidity} records represented by distinct rows of a SQL result set.
 */
class PatientMorbidityRowAccumulator {

    static Collector<PatientMorbidity, PatientMorbidityRowAccumulator, List<PatientMorbidity>> accumulating() {
        return Collectors.collectingAndThen(
            Collector.of(
                () -> new PatientMorbidityRowAccumulator(new PatientMorbidityRowMerger()),
                PatientMorbidityRowAccumulator::accumulate,
                PatientMorbidityRowAccumulator::merge
            ),
            PatientMorbidityRowAccumulator::accumulated
        );
    }

    private final PatientMorbidityRowMerger merger;
    private final Map<Long, PatientMorbidity> accumulated;

    PatientMorbidityRowAccumulator(final PatientMorbidityRowMerger merger) {
        this(merger, new HashMap<>());
    }

    private PatientMorbidityRowAccumulator(final PatientMorbidityRowMerger merger,
        final Map<Long, PatientMorbidity> accumulated) {
        this.merger = merger;
        this.accumulated = accumulated;
    }

    PatientMorbidityRowAccumulator accumulate(final PatientMorbidity morbidity) {

        this.accumulated.compute(
            morbidity.morbidity(),
            (id, existing) -> existing == null
                ? morbidity
                : this.merger.merge(existing, morbidity)
        );

        return this;
    }

    PatientMorbidityRowAccumulator merge(final PatientMorbidityRowAccumulator other) {
        other.accumulated.forEach((id, morbidity) -> this.accumulated.merge(id, morbidity, this.merger::merge));
        return this;
    }

    List<PatientMorbidity> accumulated() {
        return List.copyOf(accumulated.values());
    }
}
