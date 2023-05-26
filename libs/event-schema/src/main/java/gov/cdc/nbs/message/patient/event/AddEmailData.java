package gov.cdc.nbs.message.patient.event;

import java.io.Serializable;
import java.time.Instant;

public record AddEmailData(
                long patientId,
                long id,
                String requestId,
                long updatedBy,
                Instant asOf,
                String emailAddress) implements Serializable {
}
