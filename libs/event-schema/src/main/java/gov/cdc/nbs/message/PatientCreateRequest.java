package gov.cdc.nbs.message;

import gov.cdc.nbs.message.enums.Deceased;
import gov.cdc.nbs.message.enums.Gender;
import gov.cdc.nbs.message.enums.Suffix;

import java.time.Instant;
import java.util.Collection;

public record PatientCreateRequest(

        String request,
        Long patient,
        String patientLocalId,
        String ssn,
        Instant dateOfBirth,
        Gender birthGender,
        Gender currentGender,
        Deceased deceased,
        Instant deceasedTime,
        String maritalStatus,
        Collection<Name> names,
        String ethnicity,
        Collection<String> races,
        Collection<PostalAddress> addresses,
        Collection<PhoneNumber> phoneNumbers,
        Collection<EmailAddress> emailAddresses,
        long createdBy,
        Instant createdAt
) {
    public PatientCreateRequest(
            String requestId,
            Long patientId,
            String patientLocalId,
            String ssn,
            Instant dateOfBirth,
            Gender birthGender,
            Gender currentGender,
            Deceased deceased,
            Instant deceasedTime,
            String maritalStatus,
            Collection<Name> names,
            String ethnicity,
            Collection<String> races,
            Collection<PostalAddress> addresses,
            Collection<PhoneNumber> phoneNumbers,
            Collection<EmailAddress> emailAddresses,
            long createdBy
    ) {
        this(
                requestId,
                patientId,
                patientLocalId,
                ssn,
                dateOfBirth,
                birthGender,
                currentGender,
                deceased,
                deceasedTime,
                maritalStatus,
                names,
                ethnicity,
                races,
                addresses,
                phoneNumbers,
                emailAddresses,
                createdBy,
                Instant.now()
        );
    }

    public record Name(
            String first,
            String middle,
            String last,
            Suffix suffix,
            PatientInput.NameUseCd use
    ) {
    }

    public record PostalAddress(
            long id,
            String streetAddress1,
            String streetAddress2,
            String city,
            String stateCode,
            String countyCode,
            String countryCode,
            String zip,
            String censusTract
    ) {
    }

    public record EmailAddress(
            long id,
            String emailAddress
    ) {
    }

    public record PhoneNumber(
            long id,
            String number,
            String extension,
            PatientInput.PhoneType phoneType
    ) {
    }
}


