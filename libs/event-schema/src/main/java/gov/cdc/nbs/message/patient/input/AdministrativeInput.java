package gov.cdc.nbs.message.patient.input;

import java.time.Instant;

import gov.cdc.nbs.message.patient.event.PatientRequest;
import gov.cdc.nbs.message.patient.event.UpdateAdministrativeData;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AdministrativeInput {
    private long patientId;
    private String description;

    public static PatientRequest toRequest(
            final long userId,
            final String requestId,
            final AdministrativeInput input) {
        return new PatientRequest.updateAdministrative(
                requestId,
                input.getPatientId(),
                userId,
                new UpdateAdministrativeData(
                        input.getPatientId(),
                        requestId,
                        userId,
                        Instant.now(),
                        input.getDescription()));

    };
}
