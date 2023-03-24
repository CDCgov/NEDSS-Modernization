package gov.cdc.nbs.patient.create;

import gov.cdc.nbs.message.patient.event.PatientCreateData;
import gov.cdc.nbs.message.patient.event.PatientRequest;
import gov.cdc.nbs.message.patient.input.PatientInput;
import gov.cdc.nbs.patient.IdGeneratorService;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class PatientCreateRequestResolver {

    private final IdGeneratorService idGeneratorService;

    public PatientCreateRequestResolver(final IdGeneratorService idGeneratorService) {
        this.idGeneratorService = idGeneratorService;
    }

    public PatientRequest create(
            final long requester,
            final String request,
            final PatientInput input
    ) {

        var patientId = generateNbsId();

        return new PatientRequest.Create(
                request,
                patientId,
                requester,
                new PatientCreateData(
                        request,
                        patientId,
                        generateLocalId(),
                        input.getSsn(),
                        input.getDateOfBirth(),
                        input.getBirthGender(),
                        input.getCurrentGender(),
                        input.getDeceased(),
                        input.getDeceasedTime(),
                        input.getMaritalStatus(),
                        asNames(input.getNames()),
                        input.getEthnicityCode(),
                        input.getRaceCodes(),
                        asPostalAddresses(input.getAddresses()),
                        asPhoneNumbers(input.getPhoneNumbers()),
                        asEmailAddresses(input.getEmailAddresses()),
                        requester,
                        input.getAsOf(),
                        input.getComments())
        );
    }

    private Collection<PatientCreateData.Name> asNames(final Collection<PatientInput.Name> names) {
        return names.stream()
                .map(this::asName)
                .toList();
    }

    private PatientCreateData.Name asName(final PatientInput.Name name) {
        return new PatientCreateData.Name(
                name.getFirstName(),
                name.getMiddleName(),
                name.getLastName(),
                name.getSuffix(),
                name.getNameUseCd());
    }

    private Collection<PatientCreateData.PostalAddress> asPostalAddresses(
            final Collection<PatientInput.PostalAddress> addresses) {
        return addresses.stream()
                .map(this::asPostalAddress)
                .toList();
    }

    private PatientCreateData.PostalAddress asPostalAddress(final PatientInput.PostalAddress address) {
        long id = generateNbsId();

        return new PatientCreateData.PostalAddress(
                id,
                address.getStreetAddress1(),
                address.getStreetAddress2(),
                address.getCity(),
                address.getStateCode(),
                address.getCountyCode(),
                address.getCountryCode(),
                address.getZip(),
                address.getCensusTract());
    }

    private Collection<PatientCreateData.PhoneNumber> asPhoneNumbers(
            final Collection<PatientInput.PhoneNumber> phoneNumbers) {
        return phoneNumbers.stream()
                .map(this::asPhoneNumber)
                .toList();
    }

    private PatientCreateData.PhoneNumber asPhoneNumber(final PatientInput.PhoneNumber phoneNumber) {
        long id = generateNbsId();

        return new PatientCreateData.PhoneNumber(
                id,
                phoneNumber.getNumber(),
                phoneNumber.getExtension(),
                phoneNumber.getPhoneType());
    }

    private Collection<PatientCreateData.EmailAddress> asEmailAddresses(final Collection<String> emails) {
        return emails.stream()
                .map(this::asEmailAddress)
                .toList();
    }

    private PatientCreateData.EmailAddress asEmailAddress(final String emailAddress) {
        long id = generateNbsId();

        return new PatientCreateData.EmailAddress(
                id,
                emailAddress);
    }

    /**
     * Calls the id generator service and constructs the localId with the format "prefix + id + suffix"
     */
    private String generateLocalId() {
        var generatedId = idGeneratorService.getNextValidId(IdGeneratorService.EntityType.PERSON);
        return generatedId.getPrefix() + generatedId.getId() + generatedId.getSuffix();
    }

    /**
     * Calls the id generator service to retrieve the next available Id for an entity
     */
    private Long generateNbsId() {
        var generatedId = idGeneratorService.getNextValidId(IdGeneratorService.EntityType.NBS);
        return generatedId.getId();
    }
}
