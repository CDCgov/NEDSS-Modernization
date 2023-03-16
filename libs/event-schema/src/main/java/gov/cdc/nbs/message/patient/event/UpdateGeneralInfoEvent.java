package gov.cdc.nbs.message.patient.event;

import java.time.Instant;

public record UpdateGeneralInfoEvent(
        long patientId,
        String requestId,
        long updatedBy,
        Instant asOf,
        String maritalStatus,
        String mothersMaidenName,
        Short adultsInHouseNumber,
        Short childrenInHouseNumber,
        String occupationCode,
        String educationLevelCode,
        String primaryLanguageCode,
        String speaksEnglishCode,
        String eharsId) {

}
