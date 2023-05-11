package gov.cdc.nbs.message.patient.event;

import java.io.Serializable;
import java.time.Instant;
import gov.cdc.nbs.message.enums.Suffix;

public record UpdateNameData(
        long patientId,
        short personNameSeq,
        String requestId,
        long updatedBy,
        Instant asOf,
        String first,
        String middle,
        String last,
        Suffix suffix,
        String type) implements Serializable {
}
