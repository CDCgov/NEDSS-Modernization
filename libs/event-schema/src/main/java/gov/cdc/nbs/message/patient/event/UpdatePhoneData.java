package gov.cdc.nbs.message.patient.event;

import java.io.Serializable;
import java.time.Instant;
import gov.cdc.nbs.message.patient.input.PatientInput.PhoneType;

public record UpdatePhoneData(
                long patientId,
                long id,
                String requestId,
                long updatedBy,
                Instant asOf,
                String number,
                String extension,
                PhoneType phoneType) implements Serializable {
}
