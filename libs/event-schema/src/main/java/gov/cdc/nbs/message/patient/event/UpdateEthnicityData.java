package gov.cdc.nbs.message.patient.event;

import java.io.Serializable;
import java.time.Instant;

public record UpdateEthnicityData(
                long patientId,
                String requestId,
                long updatedBy,
                Instant asOf,
                String ethnicityGroupInd) implements Serializable {
}
