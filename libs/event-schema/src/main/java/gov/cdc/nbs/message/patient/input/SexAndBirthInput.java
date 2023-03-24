package gov.cdc.nbs.message.patient.input;

import gov.cdc.nbs.message.enums.Gender;
import gov.cdc.nbs.message.patient.event.PatientRequest;
import gov.cdc.nbs.message.patient.event.UpdateSexAndBirthData;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class SexAndBirthInput {
    private Instant asOf;
    private long patientId;
    private LocalDate dateOfBirth;
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

    public static PatientRequest toRequest(
        final long userId,
        final String requestId,
            final SexAndBirthInput input
    ) {
        return new PatientRequest.UpdateSexAndBirth(
                requestId,
                input.getPatientId(),
                userId,
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
                        input.getAgeReportedTime())
        );
    }
}
