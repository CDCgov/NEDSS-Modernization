package gov.cdc.nbs.message.patient.event;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(PatientEvent.Created.class),
    @JsonSubTypes.Type(PatientEvent.Deleted.class),
    @JsonSubTypes.Type(PatientEvent.EthnicityChanged.class)
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
        String birthGender,
        String currentGender,
        String deceased,
        Instant deceasedOn,
        String maritalStatus,
        String ethnicGroup,
        Instant asOf,
        String comments,
        String stateHIVCase,
        List<Name> names,
        List<String> races,
        List<Address> addresses,
        List<Phone> phoneNumbers,
        List<Email> emails,
        List<Identification> identifications,
        long createdBy,
        Instant createdOn
    ) implements PatientEvent {
        public record Name(String use, String first, String middle, String last, String suffix) {
        }


        public record Address(
            long identifier,
            String streetAddress1,
            String streetAddress2,
            String city,
            String state,
            String zip,
            String county,
            String country,
            String censusTract
        ) {
        }


        public record Phone(long identifier, String type, String use, String number, String extension) {
        }


        public record Email(long identifier, String type, String use, String address) {
        }


        public record Identification(int identifier, String type, String authority, String value) {
        }
    }


    record Deleted(
        long patient,
        String localId,
        long deletedBy,
        Instant deletedOn
    ) implements PatientEvent {
    }


    record EthnicityChanged(
        long patient,
        String localId,
        Instant asOf,
        String ethnicGroup,
        String unknownReason,
        List<String> detailed,
        long changedBy,
        Instant changedOn
    ) implements PatientEvent {
    }
}
