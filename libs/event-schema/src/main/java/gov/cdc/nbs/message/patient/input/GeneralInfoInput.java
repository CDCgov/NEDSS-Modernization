package gov.cdc.nbs.message.patient.input;

import gov.cdc.nbs.message.patient.event.PatientRequest;
import gov.cdc.nbs.message.patient.event.UpdateGeneralInfoData;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
public class GeneralInfoInput {
    private long patientId;
    private Instant asOf;
    private String maritalStatus;
    private String mothersMaidenName;
    private Short adultsInHouseNumber;
    private Short childrenInHouseNumber;
    private String occupationCode;
    private String educationLevelCode;
    private String primaryLanguageCode;
    private String speaksEnglishCode;
    private String eharsId;

    public static PatientRequest toRequest(
        final long userId,
        final String requestId,
        final GeneralInfoInput input
    ) {
        return new PatientRequest.UpdateGeneralInfo(
                requestId,
                input.getPatientId(),
                userId,
                new UpdateGeneralInfoData(
                        input.getPatientId(),
                        requestId,
                        userId,
                        input.getAsOf(),
                        input.getMaritalStatus(),
                        input.getMothersMaidenName(),
                        input.getAdultsInHouseNumber(),
                        input.getChildrenInHouseNumber(),
                        input.getOccupationCode(),
                        input.getEducationLevelCode(),
                        input.getPrimaryLanguageCode(),
                        input.getSpeaksEnglishCode(),
                        input.getEharsId()
                )
        );
    }
}
