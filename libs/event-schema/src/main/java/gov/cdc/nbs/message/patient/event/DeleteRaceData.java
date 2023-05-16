package gov.cdc.nbs.message.patient.event;

import java.io.Serializable;
import java.time.Instant;

public record DeleteRaceData(
                long patientId,
                String requestId,
                long updatedBy,
                Instant asOf,
                String raceCd) implements Serializable {
}
