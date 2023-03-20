package gov.cdc.nbs.message.patient.event;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import gov.cdc.nbs.message.enums.Gender;

public record UpdateSexAndBirthData(
        String requestId,
        long patientId,
        long updatedBy,
        Instant asOf,
        LocalDate dateOfBirth,
        Gender birthGender,
        Gender currentGender,
        String additionalGender,
        String transGenderInfo,
        String birthCity,
        String birthCntry,
        String birthState,
        Short birthOrderNbr,
        String multipleBirth,
        String sexUnknown,
        String currentAge,
        Instant ageReportedTime) implements Serializable {
}
