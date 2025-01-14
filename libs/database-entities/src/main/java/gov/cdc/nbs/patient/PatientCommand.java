package gov.cdc.nbs.patient;

import gov.cdc.nbs.message.enums.Deceased;
import gov.cdc.nbs.message.enums.Gender;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

public sealed interface PatientCommand {

  long person();

  long requester();

  Instant requestedOn();

  record CreatePatient(
      long person,
      String localId,
      long requester,
      Instant requestedOn
  ) implements PatientCommand {}

  record AddPatient(
      long person,
      String localId,
      LocalDate dateOfBirth,
      Gender birthGender,
      Gender currentGender,
      Deceased deceased,
      Instant deceasedTime,
      String maritalStatus,
      String ethnicityCode,
      Instant asOf,
      String comments,
      String stateHIVCase,
      long requester,
      Instant requestedOn
  ) implements PatientCommand {
  }


  record AddRace(
      long person,
      Instant asOf,
      String category,
      List<String> detailed,
      long requester,
      Instant requestedOn
  ) implements PatientCommand {

    public AddRace(
        long person,
        Instant asOf,
        String category,
        long requester,
        Instant requestedOn
    ) {
      this(person, asOf, category, List.of(), requester, requestedOn);
    }

  }


  record AddRaceCategory(
      long person,
      Instant asOf,
      String category,
      long requester,
      Instant requestedOn
  ) implements PatientCommand {

  }


  record AddDetailedRace(
      long person,
      Instant asOf,
      String category,
      String race,
      long requester,
      Instant requestedOn
  ) implements PatientCommand {

  }


  record UpdateRaceInfo(
      long person,
      Instant asOf,
      String category,
      List<String> detailed,
      long requester,
      Instant requestedOn
  ) implements PatientCommand {
  }


  record DeleteRaceInfo(
      long person,
      String category,
      long requester,
      Instant requestedOn
  ) implements PatientCommand {
  }


  record AddAddress(
      long person,
      long id,
      Instant asOf,
      String type,
      String use,
      String address1,
      String address2,
      String city,
      String state,
      String zip,
      String county,
      String country,
      String censusTract,
      String comments,
      long requester,
      Instant requestedOn
  ) implements PatientCommand {

    public AddAddress(
        long person,
        long id,
        Instant asOf,
        String address1,
        String address2,
        String city,
        String state,
        String zip,
        String county,
        String country,
        String censusTract,
        long requester,
        Instant requestedOn
    ) {
      this(
          person,
          id,
          asOf,
          "H",
          "H",
          address1,
          address2,
          city,
          state,
          zip,
          county,
          country,
          censusTract,
          null,
          requester,
          requestedOn
      );
    }

  }


  record UpdateAddress(
      long person,
      long id,
      Instant asOf,
      String type,
      String use,
      String address1,
      String address2,
      String city,
      String state,
      String zip,
      String county,
      String country,
      String censusTract,
      String comments,
      long requester,
      Instant requestedOn
  ) implements PatientCommand {
  }


  record DeleteAddress(
      long person,
      long id,
      long requester,
      Instant requestedOn) implements PatientCommand {
  }


  record AddPhoneNumber(
      long person,
      long id,
      Instant asOf,
      String type,
      String use,
      String number,
      String extension,
      long requester,
      Instant requestedOn
  ) implements PatientCommand {
  }


  record AddEmailAddress(
      long person,
      long id,
      Instant asOf,
      String email,
      long requester,
      Instant requestedOn
  ) implements PatientCommand {
  }


  record UpdateEmailAddress(
      long person,
      long id,
      String email,
      long requester,
      Instant requestedOn) implements PatientCommand {
  }


  record DeleteEmailAddress(
      long person,
      long id,
      long requester,
      Instant requestedOn) implements PatientCommand {
  }


  record AddPhone(
      long person,
      long id,
      String type,
      String use,
      Instant asOf,
      String countryCode,
      String number,
      String extension,
      String email,
      String url,
      String comment,
      long requester,
      Instant requestedOn
  ) implements PatientCommand {

  }


  record UpdatePhone(
      long person,
      long id,
      String type,
      String use,
      Instant asOf,
      String countryCode,
      String number,
      String extension,
      String email,
      String url,
      String comment,
      long requester,
      Instant requestedOn
  ) implements PatientCommand {

  }


  record DeletePhone(
      long person,
      long id,
      long requester,
      Instant requestedOn
  ) implements PatientCommand {

  }


  record UpdateMortality(
      long person,
      Instant asOf,
      String deceased,
      LocalDate deceasedOn,
      String city,
      String state,
      String county,
      String country,
      long requester,
      Instant requestedOn
  ) implements PatientCommand {
  }


  record UpdateEthnicityInfo(
      long person,
      Instant asOf,
      String ethnicGroup,
      String unknownReason,

      long requester,
      Instant requestedOn
  ) implements PatientCommand {
  }


  record AddDetailedEthnicity(
      long person,
      String ethnicity,
      long requester,
      Instant requestedOn
  ) implements PatientCommand {
  }


  record RemoveDetailedEthnicity(
      long person,
      String ethnicity,
      long requester,
      Instant requestedOn
  ) implements PatientCommand {
  }


  record UpdateAdministrativeInfo(
      long person,
      Instant asOf,
      String comment,
      long requester,
      Instant requestedOn
  ) implements PatientCommand {
  }


  record UpdateGeneralInfo(
      long person,
      Instant asOf,
      String maritalStatus,
      String mothersMaidenName,
      Integer adultsInHouseNumber,
      Integer childrenInHouseNumber,
      String occupationCode,
      String educationLevelCode,
      String primaryLanguageCode,
      String speaksEnglishCode,
      long requester,
      Instant requestedOn) implements PatientCommand {
  }


  record UpdateBirth(
      long person,
      Instant asOf,
      LocalDate bornOn,
      String gender,
      String multipleBirth,
      Integer birthOrder,
      String city,
      String state,
      String county,
      String country,
      long requester,
      Instant requestedOn
  ) implements PatientCommand {
  }


  record UpdateGender(
      long person,
      Instant asOf,
      String current,
      String unknownReason,
      String preferred,
      String additional,
      long requester,
      Instant requestedOn
  ) implements PatientCommand {
  }


  record Delete(
      long person,
      long requester,
      Instant requestedOn) implements PatientCommand {
  }


  record AddIdentification(
      long person,
      Instant asOf,
      String identificationNumber,
      String assigningAuthority,
      String identificationType,
      long requester,
      Instant requestedOn
  ) implements PatientCommand {

  }


  record UpdateIdentification(
      long person,
      int id,
      Instant asOf,
      String identificationNumber,
      String assigningAuthority,
      String identificationType,
      long requester,
      Instant requestedOn
  ) implements PatientCommand {
  }


  record DeleteIdentification(
      long person,
      int id,
      long requester,
      Instant requestedOn
  ) implements PatientCommand {
  }


  record AddName(
      long person,
      Instant asOf,
      String prefix,
      String first,
      String middle,
      String secondMiddle,
      String last,
      String secondLast,
      String suffix,
      String degree,
      String type,
      long requester,
      Instant requestedOn
  ) implements PatientCommand {

    public AddName(
        long person,
        Instant asOf,
        String first,
        String middle,
        String last,
        String suffix,
        String type,
        long requester,
        Instant requestedOn
    ) {
      this(
          person,
          asOf,
          null,
          first,
          middle,
          null,
          last,
          null,
          suffix,
          null,
          type,
          requester,
          requestedOn
      );
    }

  }


  record UpdateNameInfo(
      long person,
      int sequence,
      Instant asOf,
      String prefix,
      String first,
      String middle,
      String secondMiddle,
      String last,
      String secondLast,
      String suffix,
      String degree,
      String type,
      long requester,
      Instant requestedOn
  ) implements PatientCommand {
  }


  record DeleteNameInfo(
      long person,
      int sequence,
      long requester,
      Instant requestedOn
  ) implements PatientCommand {
  }

  record AssociateStateHIVCase(
      long person,
      String stateHIVCase,
      long requester,
      Instant requestedOn
  ) implements PatientCommand {
  }
}
