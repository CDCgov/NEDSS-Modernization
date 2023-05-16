package gov.cdc.nbs.patient;

import gov.cdc.nbs.address.City;
import gov.cdc.nbs.address.Country;
import gov.cdc.nbs.address.County;
import gov.cdc.nbs.message.enums.Deceased;
import gov.cdc.nbs.message.enums.Gender;
import gov.cdc.nbs.message.enums.Suffix;
import gov.cdc.nbs.message.patient.input.PatientInput;

import java.time.Instant;
import java.time.LocalDate;

public sealed interface PatientCommand {

        long person();

        long requester();

        Instant requestedOn();

        record AddPatient(
                        long person,
                        String localId,
                        String ssn,
                        LocalDate dateOfBirth,
                        Gender birthGender,
                        Gender currentGender,
                        Deceased deceased,
                        Instant deceasedTime,
                        String maritalStatus,
                        String ethnicityCode,
                        Instant asOf,
                        String comments,
                        long requester,
                        Instant requestedOn) implements PatientCommand {
        }

        record AddName(
                        long person,
                        String first,
                        String middle,
                        String last,
                        Suffix suffix,
                        PatientInput.NameUseCd type,
                        long requester,
                        Instant requestedOn) implements PatientCommand {

        }

        record AddRace(
                        long person,
                        String category,
                        long requester,
                        Instant requestedOn) implements PatientCommand {
        }

        record AddAddress(
                        long person,
                        long id,
                        String address1,
                        String address2,
                        City city,
                        String state,
                        String zip,
                        County county,
                        Country country,
                        String censusTract,
                        long requester,
                        Instant requestedOn) implements PatientCommand {
        }

        record UpdateAddress(
                        long person,
                        long id,
                        String address1,
                        String address2,
                        City city,
                        String state,
                        String zip,
                        County county,
                        Country country,
                        String censusTract,
                        long requester,
                        Instant requestedOn) implements PatientCommand {
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
                        String number,
                        String extension,
                        PatientInput.PhoneType type,
                        long requester,
                        Instant requestedOn) implements PatientCommand {
        }

        record UpdatePhoneNumber(
                        long person,
                        long id,
                        String number,
                        String extension,
                        PatientInput.PhoneType type,
                        long requester,
                        Instant requestedOn) implements PatientCommand {
        }

        record DeletePhoneNumber(
                        long person,
                        long id,
                        long requester,
                        Instant requestedOn) implements PatientCommand {
        }

        record AddEmailAddress(
                        long person,
                        long id,
                        String email,
                        long requester,
                        Instant requestedOn) implements PatientCommand {
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

        record UpdateMortalityLocator(
                        long person,
                        Instant asOf,
                        Deceased deceased,
                        Instant deceasedTime,
                        String cityOfDeath,
                        String stateOfDeath,
                        String countyOfDeath,
                        String countryOfDeath,
                        long requester,
                        Instant requestedOn) implements PatientCommand {
        }

        record DeleteMortalityLocator(
                        long person,
                        long id,
                        long requester,
                        Instant requestedOn) implements PatientCommand {
        }

        record AddMortalityLocator(
                        long person,
                        long id,
                        Instant asOf,
                        Deceased deceased,
                        Instant deceasedTime,
                        String cityOfDeath,
                        String stateOfDeath,
                        String countyOfDeath,
                        String countryOfDeath,
                        long requester,
                        Instant requestedOn) implements PatientCommand {
        }

        record UpdateAdministrativeInfo(
                        long person,
                        Instant asOf,
                        String description,
                        long requester,
                        Instant requestedOn) implements PatientCommand {
        }

        record UpdateGeneralInfo(
                        long person,
                        Instant asOf,
                        String maritalStatus,
                        String mothersMaidenName,
                        Short adultsInHouseNumber,
                        Short childrenInHouseNumber,
                        String occupationCode,
                        String educationLevelCode,
                        String primaryLanguageCode,
                        String speaksEnglishCode,
                        String eharsId,
                        long requester,
                        Instant requestedOn) implements PatientCommand {
        }

        record UpdateNameInfo(
                        long person,
                        Instant asOf,
                        short personNameSeq,
                        String first,
                        String middle,
                        String last,
                        Suffix suffix,
                        String type,
                        long requester,
                        Instant requestedOn) implements PatientCommand {
        }

        record UpdateSexAndBirthInfo(
                        long person,
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
                        Instant ageReportedTime,
                        long requester,
                        Instant requestedOn) implements PatientCommand {
        }

        record Delete(
                        long person,
                        long requester,
                        Instant requestedOn) implements PatientCommand {
        }

        record DeleteNameInfo(
                        long person,
                        Instant asOf,
                        short personNameSeq,
                        long requester,
                        Instant requestedOn) implements PatientCommand {
        }

        record UpdateEthnicityInfo(
                        long person,
                        Instant asOf,
                        String ethnicityCode,
                        long requester,
                        Instant requestedOn) implements PatientCommand {
        }

        record UpdateRaceInfo(
                        long person,
                        String raceCd,
                        Instant asOf,
                        long requester,
                        Instant requestedOn) implements PatientCommand {
        }

        record DeleteRaceInfo(
                        long person,
                        String raceCd,
                        long requester,
                        Instant requestedOn) implements PatientCommand {
        }

        record AddIdentification(
                        long person,
                        String identificationNumber,
                        String assigningAuthority,
                        String identificationType,
                        long requester,
                        Instant requestedOn) implements PatientCommand {
        }

        record UpdateIdentification(
                        long person,
                        short id,
                        String identificationNumber,
                        String assigningAuthority,
                        String identificationType,
                        long requester,
                        Instant requestedOn) implements PatientCommand {
        }

        record DeleteIdentification(
                        long person,
                        short id,
                        long requester,
                        Instant requestedOn) implements PatientCommand {
        }
}
