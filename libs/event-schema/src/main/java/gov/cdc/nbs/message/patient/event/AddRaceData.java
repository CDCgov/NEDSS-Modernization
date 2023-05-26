package gov.cdc.nbs.message.patient.event;

import java.io.Serializable;
import java.time.Instant;

public record AddRaceData(
                long patientId,
                String requestId,
                long updatedBy,
                Instant asOf,
                String raceCd,
                String raceCategoryCd) implements Serializable {
}
