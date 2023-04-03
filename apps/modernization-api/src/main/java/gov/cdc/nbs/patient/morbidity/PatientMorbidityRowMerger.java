package gov.cdc.nbs.patient.morbidity;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Merges two {@link PatientMorbidity} instances that represent the same Morbidity record spread over two rows of a SQL
 * result set.
 */
class PatientMorbidityRowMerger {

    /**
     * Merges two {@link PatientMorbidity} instances by combining the list of treatments from the {@code current} and
     * {@code next} and by combining the list of lab order results from the {@code current} and {@code next}
     *
     * @param current A {@link PatientMorbidity}
     * @param next    Another A {@link PatientMorbidity}
     * @return The merged {@link PatientMorbidity}.
     */
    PatientMorbidity merge(final PatientMorbidity current, final PatientMorbidity next) {

        Collection<String> treatments = merged(current.treatments(), next.treatments());

        Collection<PatientMorbidity.LabOrderResult> labOrderResults = merged(current.labResults(), next.labResults());

        return new PatientMorbidity(
            current.morbidity(),
            current.receivedOn(),
            current.provider(),
            current.reportedOn(),
            current.condition(),
            current.jurisdiction(),
            current.event(),
            current.associatedWith(),
            treatments,
            labOrderResults
        );
    }

    private <V> Collection<V> merged(final Collection<V> current, final Collection<V> next) {
        return Stream.of(current, next)
            .flatMap(Collection::stream)
            .collect(Collectors.toSet());
    }
}
