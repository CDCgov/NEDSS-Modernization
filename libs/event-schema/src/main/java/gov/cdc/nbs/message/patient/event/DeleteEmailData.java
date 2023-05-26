package gov.cdc.nbs.message.patient.event;

import java.io.Serializable;
import java.time.Instant;

public record DeleteEmailData(
                long patientId,
                long id,
                String requestId,
                long updatedBy,
                Instant asOf) implements Serializable {
}
