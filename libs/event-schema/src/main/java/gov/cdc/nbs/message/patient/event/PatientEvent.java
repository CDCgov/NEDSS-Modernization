package gov.cdc.nbs.message.patient.event;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(PatientEvent.Created.class),
    @JsonSubTypes.Type(PatientEvent.Deleted.class),
    @JsonSubTypes.Type(PatientEvent.EthnicityChanged.class),
    @JsonSubTypes.Type(PatientEvent.RaceAdded.class),
    @JsonSubTypes.Type(PatientEvent.RaceChanged.class),
    @JsonSubTypes.Type(PatientEvent.RaceDeleted.class)
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
      LocalDateTime createdOn
  ) implements PatientEvent {
    public record Name(String use, String first, String middle, String last, String suffix) {
    }


    public record Address(
        long identifier,
        LocalDate asOf,
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


    public record Phone(long identifier, LocalDate asOf, String type, String use, String number, String extension) {
    }


    public record Email(long identifier, LocalDate asOf, String type, String use, String address) {
    }


    public record Identification(int identifier, LocalDate asOf, String type, String authority, String value) {
    }
  }


  record Deleted(
      long patient,
      String localId,
      long deletedBy,
      LocalDateTime deletedOn
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
      LocalDateTime changedOn
  ) implements PatientEvent {
  }


  record RaceAdded(
      long patient,
      String localId,
      Instant asOf,
      String category,
      List<String> detailed,
      long addedBy,
      Instant addedOn
  ) implements PatientEvent {
  }


  record RaceChanged(
      long patient,
      String localId,
      Instant asOf,
      String category,
      List<String> detailed,
      long changedBy,
      Instant changedOn
  ) implements PatientEvent {
  }


  record RaceDeleted(
      long patient,
      String localId,
      long changedBy,
      Instant changedOn
  ) implements PatientEvent {

  }
}
