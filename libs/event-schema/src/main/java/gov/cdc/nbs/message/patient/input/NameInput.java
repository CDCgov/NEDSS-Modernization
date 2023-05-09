package gov.cdc.nbs.message.patient.input;

import java.time.Instant;
import gov.cdc.nbs.message.enums.Suffix;
import gov.cdc.nbs.message.patient.event.PatientRequest;
import gov.cdc.nbs.message.patient.event.UpdateNameData;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NameInput {
    private long patientId;
    private short personNameSeq;
    private String firstName;
    private String middleName;
    private String lastName;
    private Suffix suffix;
    private String nameUseCd;

    public static PatientRequest toRequest(
            final long userId,
            final String requestId,
            final NameInput input) {
        return new PatientRequest.UpdateName(
            requestId,
            input.getPatientId(),
                userId,
                new UpdateNameData(
                        input.getPatientId(),
                        input.getPersonNameSeq(),
                        requestId,
                        userId,
                        Instant.now(),
                        input.getFirstName(),
                        input.getMiddleName(),
                        input.getLastName(),
                        input.getSuffix(),
                        input.getNameUseCd()));
    }
}
