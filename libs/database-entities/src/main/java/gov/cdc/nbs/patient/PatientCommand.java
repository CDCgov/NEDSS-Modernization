package gov.cdc.nbs.patient;

import gov.cdc.nbs.message.PatientInput;
import gov.cdc.nbs.message.enums.Deceased;
import gov.cdc.nbs.message.enums.Gender;
import gov.cdc.nbs.message.enums.Suffix;

import java.time.Instant;

public sealed interface PatientCommand {

    long person();

    long requester();

    Instant requestedOn();

    record AddPatient(
            long person,
            String localId,
            String ssn,
            Instant dateOfBirth,
            Gender birthGender,
            Gender currentGender,
            Deceased deceased,
            Instant deceasedTime,
            String maritalStatus,

            String ethnicityCode,
            long requester,
            Instant requestedOn
    ) implements PatientCommand {
    }

    record AddName(
            long person,
            String first,
            String middle,
            String last,
            Suffix suffix,
            PatientInput.NameUseCd type,
            long requester,
            Instant requestedOn
    ) implements PatientCommand {

    }

    record AddRace(
            long person,
            String category,
            long requester,
            Instant requestedOn
    ) implements PatientCommand {

    }

    record AddAddress(
            long person,
            long id,
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
    ) implements PatientCommand {
    }

    record AddPhoneNumber(
            long person,
            long id,
            String number,
            String extension,
            PatientInput.PhoneType type,
            long requester,
            Instant requestedOn
    ) implements PatientCommand {
    }

    record AddEmailAddress(
            long person,
            long id,
            String email,
            long requester,
            Instant requestedOn
    ) implements PatientCommand {
    }
}
