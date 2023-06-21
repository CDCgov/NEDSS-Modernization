package gov.cdc.nbs.message.patient.input;

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

}
