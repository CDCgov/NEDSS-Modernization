package gov.cdc.nbs.patient.morbidity;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

/**
 * Merges two {@link PatientMorbidity} instances that represent the same Morbidity record spread over two rows of a SQL
 * result set.
 */
class PatientMorbidityRowMerger {

    /**
     * Merges two {@link PatientMorbidity} instances by combining the list of treatments.
     *
     * @param current A {@link PatientMorbidity}
     * @param next    Another A {@link PatientMorbidity}
     * @return The merged {@link PatientMorbidity}.
     */
    PatientMorbidity merge(final PatientMorbidity current, final PatientMorbidity next) {

        List<String> treatments = Stream.of(current.treatments(), next.treatments())
            .flatMap(Collection::stream)
            .toList();

        return new PatientMorbidity(
            current.morbidity(),
            current.receivedOn(),
            current.provider(),
            current.reportedOn(),
            current.condition(),
            current.jurisdiction(),
            current.event(),
            current.associatedWith(),
            treatments
        );
    }

}
