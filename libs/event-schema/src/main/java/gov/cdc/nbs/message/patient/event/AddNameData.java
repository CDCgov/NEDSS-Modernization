package gov.cdc.nbs.message.patient.event;

import java.io.Serializable;
import java.time.Instant;
import gov.cdc.nbs.message.enums.Suffix;
import gov.cdc.nbs.message.patient.input.PatientInput.NameUseCd;

public record AddNameData(
        long patientId,
        String requestId,
        long updatedBy,
        Instant asOf,
        String first,
        String middle,
        String last,
        Suffix suffix,
        NameUseCd type) implements Serializable {
}
