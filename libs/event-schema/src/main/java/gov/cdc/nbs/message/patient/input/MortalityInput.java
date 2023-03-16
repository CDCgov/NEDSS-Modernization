package gov.cdc.nbs.message.patient.input;

import java.time.Instant;
import gov.cdc.nbs.message.enums.Deceased;
import gov.cdc.nbs.message.patient.event.PatientEvent;
import gov.cdc.nbs.message.patient.event.UpdateMortalityEvent;
import gov.cdc.nbs.message.patient.event.PatientEvent.PatientEventType;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    public static PatientEvent toEvent(final long userId, final String requestId,
            final MortalityInput input) {
        return new PatientEvent(
                requestId,
                input.getPatientId(),
                userId,
                PatientEventType.UPDATE_MORTALITY,
                new UpdateMortalityEvent(
                        input.getPatientId(),
                        requestId,
                        userId,
                        input.getAsOf(),
                        input.getDeceased(),
                        input.getDeceasedTime(),
                        input.getCityOfDeath(),
                        input.getStateOfDeath(),
                        input.getCountyOfDeath(),
                        input.getCountryOfDeath()));
    }
}
