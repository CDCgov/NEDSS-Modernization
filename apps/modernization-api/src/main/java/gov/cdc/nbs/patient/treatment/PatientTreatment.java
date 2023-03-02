package gov.cdc.nbs.patient.treatment;

import java.time.Instant;

public record PatientTreatment(
        long treatment,
        Instant createdOn,
        String provider,
        Instant treatedOn,
        String description,
        String event,
        Investigation associatedWith
) {

    public record Investigation(
            long id,
            String local,
            String condition
    ) {
    }

}
