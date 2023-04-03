package gov.cdc.nbs.patient.morbidity;

import java.time.Instant;
import java.util.Collection;

record PatientMorbidity(
    long morbidity,
    Instant receivedOn,
    String provider,
    Instant reportedOn,
    String condition,
    String jurisdiction,
    String event,
    Investigation associatedWith,
    Collection<String> treatments,
    Collection<LabOrderResult> labResults
) {

    record Investigation(
        long id,
        String local,
        String condition
    ) {
    }


    record LabOrderResult(
        String labTest,
        String status,
        String codedResult,
        String numericResult,
        String textResult
    ) {

    }
}
