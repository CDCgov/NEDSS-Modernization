package gov.cdc.nbs.patient;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public sealed interface PatientCommand {

  long person();

  long requester();

  LocalDateTime requestedOn();

  record CreatePatient(long person, String localId, long requester, LocalDateTime requestedOn)
      implements PatientCommand {}

  record AddRaceInfo(
      long person,
      LocalDate asOf,
      String category,
      List<String> detailed,
      long requester,
      LocalDateTime requestedOn)
      implements PatientCommand {}

  record AddRaceCategory(
      long person, LocalDate asOf, String category, long requester, LocalDateTime requestedOn)
      implements PatientCommand {}

  record AddDetailedRace(
      long person,
      LocalDate asOf,
      String category,
      String race,
      long requester,
      LocalDateTime requestedOn)
      implements PatientCommand {}

  record UpdateRaceInfo(
      long person,
      LocalDate asOf,
      String category,
      List<String> detailed,
      long requester,
      LocalDateTime requestedOn)
      implements PatientCommand {}

  record DeleteRaceInfo(long person, String category, long requester, LocalDateTime requestedOn)
      implements PatientCommand {}

  record AddAddress(
      long person,
      LocalDate asOf,
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
      LocalDateTime requestedOn)
      implements PatientCommand {

    public AddAddress(
        long person,
        LocalDate asOf,
        String address1,
        String address2,
        String city,
        String state,
        String zip,
        String county,
        String country,
        String censusTract,
        long requester,
        LocalDateTime requestedOn) {
      this(
          person,
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
          requestedOn);
    }
  }

  record UpdateAddress(
      long person,
      long id,
      LocalDate asOf,
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
      LocalDateTime requestedOn)
      implements PatientCommand {}

  record DeleteAddress(long person, long id, long requester, LocalDateTime requestedOn)
      implements PatientCommand {}

  record AddPhone(
      long person,
      String type,
      String use,
      LocalDate asOf,
      String countryCode,
      String number,
      String extension,
      String email,
      String url,
      String comment,
      long requester,
      LocalDateTime requestedOn)
      implements PatientCommand {}

  record UpdatePhone(
      long person,
      long identifier,
      String type,
      String use,
      LocalDate asOf,
      String countryCode,
      String number,
      String extension,
      String email,
      String url,
      String comment,
      long requester,
      LocalDateTime requestedOn)
      implements PatientCommand {}

  record DeletePhone(long person, long id, long requester, LocalDateTime requestedOn)
      implements PatientCommand {}

  record UpdateMortality(
      long person,
      LocalDate asOf,
      String deceased,
      LocalDate deceasedOn,
      String city,
      String state,
      String county,
      String country,
      long requester,
      LocalDateTime requestedOn)
      implements PatientCommand {}

  record ClearMoralityDemographics(long person, long requester, LocalDateTime requestedOn)
      implements PatientCommand {}

  record UpdateEthnicityInfo(
      long person,
      LocalDate asOf,
      String ethnicGroup,
      String unknownReason,
      long requester,
      LocalDateTime requestedOn)
      implements PatientCommand {}

  record ClearEthnicityDemographics(long person, long requester, LocalDateTime requestedOn)
      implements PatientCommand {}

  record AddDetailedEthnicity(
      long person, String ethnicity, long requester, LocalDateTime requestedOn)
      implements PatientCommand {}

  record RemoveDetailedEthnicity(
      long person, String ethnicity, long requester, LocalDateTime requestedOn)
      implements PatientCommand {}

  record UpdateAdministrativeInfo(
      long person, LocalDate asOf, String comment, long requester, LocalDateTime requestedOn)
      implements PatientCommand {}

  record ClearAdministrativeInformation(long person, long requester, LocalDateTime requestedOn)
      implements PatientCommand {}

  record UpdateGeneralInfo(
      long person,
      LocalDate asOf,
      String maritalStatus,
      String mothersMaidenName,
      Integer adultsInHouseNumber,
      Integer childrenInHouseNumber,
      String occupationCode,
      String educationLevelCode,
      String primaryLanguageCode,
      String speaksEnglishCode,
      long requester,
      LocalDateTime requestedOn)
      implements PatientCommand {}

  record ClearGeneralInformationDemographics(long person, long requester, LocalDateTime requestedOn)
      implements PatientCommand {}

  record UpdateBirth(
      long person,
      LocalDate asOf,
      LocalDate bornOn,
      String gender,
      String multipleBirth,
      Integer birthOrder,
      String city,
      String state,
      String county,
      String country,
      long requester,
      LocalDateTime requestedOn)
      implements PatientCommand {}

  record ClearBirthDemographics(long person, long requester, LocalDateTime requestedOn)
      implements PatientCommand {}

  record UpdateGender(
      long person,
      LocalDate asOf,
      String current,
      String unknownReason,
      String preferred,
      String additional,
      long requester,
      LocalDateTime requestedOn)
      implements PatientCommand {}

  record ClearGenderDemographics(long person, long requester, LocalDateTime requestedOn)
      implements PatientCommand {}

  record Delete(long person, long requester, LocalDateTime requestedOn) implements PatientCommand {}

  record AddIdentification(
      long person,
      LocalDate asOf,
      String identificationNumber,
      String assigningAuthority,
      String identificationType,
      long requester,
      LocalDateTime requestedOn)
      implements PatientCommand {}

  record UpdateIdentification(
      long person,
      int id,
      LocalDate asOf,
      String identificationNumber,
      String assigningAuthority,
      String identificationType,
      long requester,
      LocalDateTime requestedOn)
      implements PatientCommand {}

  record DeleteIdentification(long person, int id, long requester, LocalDateTime requestedOn)
      implements PatientCommand {}

  record AddName(
      long person,
      LocalDate asOf,
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
      LocalDateTime requestedOn)
      implements PatientCommand {

    public AddName(
        long person,
        LocalDate asOf,
        String first,
        String middle,
        String last,
        String suffix,
        String type,
        long requester,
        LocalDateTime requestedOn) {
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
          requestedOn);
    }
  }

  record UpdateNameInfo(
      long person,
      int sequence,
      LocalDate asOf,
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
      LocalDateTime requestedOn)
      implements PatientCommand {}

  record DeleteNameInfo(long person, short sequence, long requester, LocalDateTime requestedOn)
      implements PatientCommand {}

  record AssociateStateHIVCase(
      long person, String stateHIVCase, long requester, LocalDateTime requestedOn)
      implements PatientCommand {}

  record DisassociateStateHIVCase(long person, long requester, LocalDateTime requestedOn)
      implements PatientCommand {}
}
