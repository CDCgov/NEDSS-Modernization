package gov.cdc.nbs.message.patient.event;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import gov.cdc.nbs.message.enums.Deceased;
import gov.cdc.nbs.message.enums.Gender;

import java.time.Instant;
import java.time.LocalDate;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(PatientEvent.Deleted.class)
})
public sealed interface PatientEvent {

    long patient();

    String localId();

    default String type() {
        return PatientEvent.class.getSimpleName();
    }

    record Created(
        long patient,
        String localId,
        LocalDate dateOfBirth,
        Gender birthGender,
        Gender currentGender,
        Deceased deceased,
        Instant deceasedTime,
        String maritalStatus,
        String ethnicity,
        Instant asOf,
        String comments,
        String stateHIVCase,
        long createdBy,
        Instant createdOn
    ) implements PatientEvent {

    }

    record Deleted(
        long patient,
        String localId,
        long deletedBy,
        Instant deletedOn
    ) implements PatientEvent {
    }
}
