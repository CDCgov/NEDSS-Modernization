package gov.cdc.nbs.message.patient.input;

import java.time.Instant;
import gov.cdc.nbs.message.patient.event.PatientEvent;
import gov.cdc.nbs.message.patient.event.UpdateGeneralInfoEvent;
import gov.cdc.nbs.message.patient.event.PatientEvent.PatientEventType;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    public static PatientEvent toEvent(final long userId,
            final String requestId,
            final GeneralInfoInput input) {
        return new PatientEvent(
                requestId,
                input.getPatientId(),
                userId,
                PatientEventType.UPDATE_GENERAL_INFO,
                new UpdateGeneralInfoEvent(
                        input.getPatientId(),
                        requestId,
                        userId,
                        Instant.now(),
                        input.getMaritalStatus(),
                        input.getMothersMaidenName(),
                        input.getAdultsInHouseNumber(),
                        input.getChildrenInHouseNumber(),
                        input.getOccupationCode(),
                        input.getEducationLevelCode(),
                        input.getPrimaryLanguageCode(),
                        input.getSpeaksEnglishCode(),
                        input.getEharsId()));
    }
}
