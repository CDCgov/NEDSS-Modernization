package gov.cdc.nbs.patient.contact;

import java.time.Instant;
import java.util.Collection;

public record PatientContacts(
        long patient,
        Collection<NamedByPatient> namedByPatient,
        Collection<NamedByContact> namedByContact
) {

    record NamedContact(
            long id,
            String name
    ) {

    }


    record NamedByPatient(
            long contactRecord,
            Instant createdOn,
            String condition,
            NamedContact contact,
            Instant namedOn,
            String priority,
            String disposition,
            String event
    ) {

    }


    record NamedByContact(
            long contactRecord,
            Instant createdOn,
            String condition,
            NamedContact contact,
            Instant namedOn,
            String event,
            Investigation associatedWith
    ) {

    }


    public record Investigation(
            long id,
            String local,
            String condition
    ) {
    }
}
