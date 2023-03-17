package gov.cdc.nbs.message.patient.input;

import java.time.Instant;
import gov.cdc.nbs.message.enums.Gender;
import gov.cdc.nbs.message.patient.event.PatientEvent;
import gov.cdc.nbs.message.patient.event.UpdateSexAndBirthData;
import gov.cdc.nbs.message.patient.event.PatientEvent.PatientEventType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SexAndBirthInput {
    private Instant asOf;
    private long patientId;
    private Instant dateOfBirth;
    private Gender birthGender;
    private Gender currentGender;
    private String additionalGender;
    private String transGenderInfo;
    private String birthCity;
    private String birthCntry;
    private String birthState;
    private Short birthOrderNbr;
    private String multipleBirth;
    private String sexUnknown;
    private String currentAge;
    private Instant ageReportedTime;

    public static PatientEvent toEvent(final long userId, final String requestId,
            final SexAndBirthInput input) {
        return new PatientEvent(
                requestId,
                input.getPatientId(),
                userId,
                PatientEventType.UPDATE_SEX_AND_BIRTH,
                new UpdateSexAndBirthData(
                        requestId,
                        input.getPatientId(),
                        userId,
                        Instant.now(),
                        input.getDateOfBirth(),
                        input.getBirthGender(),
                        input.getCurrentGender(),
                        input.getAdditionalGender(),
                        input.getTransGenderInfo(),
                        input.getBirthCity(),
                        input.getBirthCntry(),
                        input.getBirthState(),
                        input.getBirthOrderNbr(),
                        input.getMultipleBirth(),
                        input.getSexUnknown(),
                        input.getCurrentAge(),
                        input.getAgeReportedTime()));
    }
}
