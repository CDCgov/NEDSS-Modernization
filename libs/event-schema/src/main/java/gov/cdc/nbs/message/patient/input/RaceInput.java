package gov.cdc.nbs.message.patient.input;

import gov.cdc.nbs.message.patient.event.PatientRequest;
import gov.cdc.nbs.message.patient.event.UpdateRaceData;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
public class RaceInput {
    private long patientId;
    private Instant asOf;
    private String raceCd;

    public static PatientRequest toAddRequest(
            final long userId,
            final String requestId,
            final RaceInput input) {
        return new PatientRequest.AddRace(
                requestId,
                input.getPatientId(),
                userId,
                new UpdateRaceData(
                        input.getPatientId(),
                        requestId,
                        userId,
                        input.getAsOf(),
                        input.getRaceCd()));
    }

    public static PatientRequest toUpdateRequest(
            final long userId,
            final String requestId,
            final RaceInput input) {
        return new PatientRequest.UpdateRace(
                requestId,
                input.getPatientId(),
                userId,
                new UpdateRaceData(
                        input.getPatientId(),
                        requestId,
                        userId,
                        input.getAsOf(),
                        input.getRaceCd()));
    }
}
