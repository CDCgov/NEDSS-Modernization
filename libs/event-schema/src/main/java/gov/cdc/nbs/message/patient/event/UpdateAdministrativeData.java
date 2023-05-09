package gov.cdc.nbs.message.patient.event;

import java.io.Serializable;
import java.time.Instant;

public record UpdateAdministrativeData(
        long patientId,
        String requestId,
        long updatedBy,
        Instant asOf,
        String description) implements Serializable {
}
