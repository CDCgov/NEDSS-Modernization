package gov.cdc.nbs.message.patient.event;

import java.io.Serializable;
import java.time.Instant;

public record UpdateRaceData(
        long patientId,
        String requestId,
        long updatedBy,
        Instant asOf,
        String raceCd) implements Serializable {
}
