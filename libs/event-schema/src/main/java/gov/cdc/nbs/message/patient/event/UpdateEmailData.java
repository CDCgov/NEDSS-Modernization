package gov.cdc.nbs.message.patient.event;

import java.io.Serializable;
import java.time.Instant;

public record UpdateEmailData(
                long patientId,
                long id,
                String requestId,
                long updatedBy,
                Instant asOf,
                String emailAddress) implements Serializable {
}
