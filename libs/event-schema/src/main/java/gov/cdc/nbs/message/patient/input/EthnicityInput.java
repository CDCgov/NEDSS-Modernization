package gov.cdc.nbs.message.patient.input;

import gov.cdc.nbs.message.patient.event.PatientRequest;
import gov.cdc.nbs.message.patient.event.UpdateEthnicityData;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
public class EthnicityInput {
    private long patientId;
    private Instant asOf;
    private String ethnicityCode;

    public static PatientRequest toRequest(
            final long userId,
            final String requestId,
            final EthnicityInput input) {
        return new PatientRequest.UpdateEthnicity(
                requestId,
                input.getPatientId(),
                userId,
                new UpdateEthnicityData(
                        input.getPatientId(),
                        requestId,
                        userId,
                        input.getAsOf(),
                        input.getEthnicityCode()));
    }
}
