package gov.cdc.nbs.message.patient.input;

import gov.cdc.nbs.message.enums.Deceased;
import gov.cdc.nbs.message.patient.event.PatientRequest;
import gov.cdc.nbs.message.patient.event.UpdateMortalityData;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
public class MortalityInput {
    private long patientId;
    private Instant asOf;
    private Deceased deceased;
    private Instant deceasedTime;
    private String cityOfDeath;
    private String stateOfDeath;
    private String countyOfDeath;
    private String countryOfDeath;

    public static PatientRequest toRequest(
        final long userId,
        final String requestId,
        final MortalityInput input
    ) {
        return new PatientRequest.UpdateMortality(
                requestId,
                input.getPatientId(),
                userId,
                new UpdateMortalityData(
                        input.getPatientId(),
                        requestId,
                        userId,
                        input.getAsOf(),
                        input.getDeceased(),
                        input.getDeceasedTime(),
                        input.getCityOfDeath(),
                        input.getStateOfDeath(),
                        input.getCountyOfDeath(),
                        input.getCountryOfDeath())
        );
    }
}
