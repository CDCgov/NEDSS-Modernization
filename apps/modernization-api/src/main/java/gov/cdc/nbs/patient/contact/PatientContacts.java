package gov.cdc.nbs.patient.contact;

import java.time.Instant;
import java.util.Collection;

public record PatientContacts(
        long patient,
        Collection<NamedByPatient> namedByPatient
) {

    record NamedContact(
            long id,
            String name
    ) {

    }

    record NamedByPatient(
            long contactRecord,
            Instant createdOn,
            NamedContact contact,
            Instant namedOn,
            String priority,
            String disposition,
            String event,
            Investigation investigation
    ) {

    }

    public record Investigation(
            long id,
            String local,
            String condition
    ) {
    }
}
