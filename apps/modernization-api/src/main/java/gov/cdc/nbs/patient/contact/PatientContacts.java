package gov.cdc.nbs.patient.contact;

import java.time.Instant;
import java.util.Collection;
import gov.cdc.nbs.event.investigation.association.AssociatedWith;

public record PatientContacts(
        long patient,
        Collection<NamedByPatient> namedByPatient,
        Collection<NamedByContact> namedByContact) {

    record NamedContact(
            String local,
            String name) {

    }
    record Condition(
            String id,
            String description) {

    }
    record NamedByPatient(
            long contactRecord,
            Instant createdOn,
            Condition condition,
            NamedContact contact,
            Instant namedOn,
            String priority,
            String disposition,
            String event,
            Investigation associatedWith) {

    }


    record NamedByContact(
            long contactRecord,
            Instant createdOn,
            Condition condition,
            NamedContact contact,
            Instant namedOn,
            String event,
            AssociatedWith associatedWith) {

    }


    public record Investigation(
            long id,
            String local,
            String condition) {
    }
}
