package gov.cdc.nbs.message.patient.event;

import java.io.Serializable;
import java.time.Instant;

import gov.cdc.nbs.message.patient.input.PatientInput.PhoneType;

public record AddPhoneData(
                long patientId,
                String requestId,
                long updatedBy,
                Instant asOf,
                String number,
                String extension,
                PhoneType phoneType) implements Serializable {
}
