package gov.cdc.nbs.message.patient.event;

import java.io.Serializable;
import java.time.Instant;

public record UpdateIdentificationData(
                long patientId,
                short id,
                String requestId,
                long updatedBy,
                Instant asOf,
                String identificationNumber,
                String assigningAuthority,
                String identificationType) implements Serializable {
}
