package gov.cdc.nbs.message.patient.event;

import java.io.Serializable;
import java.time.Instant;

public record DeleteNameData(
        long patientId,
        short personNameSeq,
        String requestId,
        long updatedBy,        
        Instant asOf) implements Serializable {
}
