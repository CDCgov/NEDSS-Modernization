package gov.cdc.nbs.patientlistener.request.update;

import gov.cdc.nbs.address.City;
import gov.cdc.nbs.address.Country;
import gov.cdc.nbs.address.County;
import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.entity.odse.PostalEntityLocatorParticipation;
import gov.cdc.nbs.message.patient.event.AddAddressData;
import gov.cdc.nbs.message.patient.event.AddEmailData;
import gov.cdc.nbs.message.patient.event.AddIdentificationData;
import gov.cdc.nbs.message.patient.event.AddNameData;
import gov.cdc.nbs.message.patient.event.AddPhoneData;
import gov.cdc.nbs.message.patient.event.DeleteAddressData;
import gov.cdc.nbs.message.patient.event.DeleteEmailData;
import gov.cdc.nbs.message.patient.event.DeleteIdentificationData;
import gov.cdc.nbs.message.patient.event.DeleteMortalityData;
import gov.cdc.nbs.message.patient.event.DeleteNameData;
import gov.cdc.nbs.message.patient.event.DeletePhoneData;
import gov.cdc.nbs.message.patient.event.UpdateAddressData;
import gov.cdc.nbs.message.patient.event.UpdateAdministrativeData;
import gov.cdc.nbs.message.patient.event.UpdateEmailData;
import gov.cdc.nbs.message.patient.event.UpdateGeneralInfoData;
import gov.cdc.nbs.message.patient.event.UpdateIdentificationData;
import gov.cdc.nbs.message.patient.event.UpdateMortalityData;
import gov.cdc.nbs.message.patient.event.UpdateNameData;
import gov.cdc.nbs.message.patient.event.UpdatePhoneData;
import gov.cdc.nbs.message.patient.event.UpdateSexAndBirthData;
import gov.cdc.nbs.id.IdGeneratorService;
import gov.cdc.nbs.patient.PatientCommand;
import gov.cdc.nbs.repository.PersonRepository;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Component
public class PatientUpdater {
    private final IdGeneratorService idGenerator;
    private final PersonRepository personRepository;

    public PatientUpdater(
        IdGeneratorService idGenerator,
        PersonRepository personRepository) {
        this.idGenerator = idGenerator;
        this.personRepository = personRepository;
    }

    public Person update(final Person person, final UpdateGeneralInfoData data) {
        person.update(asUpdateGeneralInfo(data));
        return personRepository.save(person);
    }

    public Person update(final Person person, final UpdateAdministrativeData data) {
        person.update(asUpdateAdministrativeInfo(data));
        return personRepository.save(person);
    }

    public Person update(final Person person, final AddNameData data) {
        person.update(asAddNameInfo(data));
        return personRepository.save(person);
    }

    public Person update(final Person person, final UpdateNameData data) {
        person.update(asUpdateNameInfo(data));
        return personRepository.save(person);
    }

    public Person update(final Person person, final DeleteNameData data) {
        person.update(asDeleteNameInfo(data));
        return personRepository.save(person);
    }

    public Person update(final Person person, final AddPhoneData data) {
        person.add(asAddPhoneInfo(data));
        return personRepository.save(person);
    }

    public Person update(final Person person, final UpdatePhoneData data) {
        person.update(asUpdatePhoneInfo(data));
        return personRepository.save(person);
    }

    public Person update(final Person person, final DeletePhoneData data) {
        person.delete(asDeletePhoneInfo(data));
        return personRepository.save(person);
    }

    public Person update(final Person person, final AddEmailData data) {
        person.add(asAddEmailInfo(data));
        return personRepository.save(person);
    }

    public Person update(final Person person, final UpdateEmailData data) {
        person.update(asUpdateEmailInfo(data));
        return personRepository.save(person);
    }

    public Person update(final Person person, final DeleteEmailData data) {
        person.delete(asDeleteEmailInfo(data));
        return personRepository.save(person);
    }

    public Person update(final Person person, final AddAddressData data) {
        person.add(asAddAddressInfo(data));
        return personRepository.save(person);
    }

    public Person update(final Person person, final UpdateAddressData data) {
        person.update(asUpdateAddressInfo(data));
        return personRepository.save(person);
    }

    public Person update(final Person person, final DeleteAddressData data) {
        person.delete(asDeleteAddressInfo(data));
        return personRepository.save(person);
    }

    public Person update(final Person person, final AddIdentificationData data) {
        person.add(asAddIdentificationInfo(data));
        return personRepository.save(person);
    }

    public Person update(final Person person, final UpdateIdentificationData data) {
        person.update(asUpdateIdentificationInfo(data));
        return personRepository.save(person);
    }

    public Person update(final Person person, final DeleteIdentificationData data) {
        person.delete(asDeleteIdentificationInfo(data));
        return personRepository.save(person);
    }

    public Person update(final Person person, final UpdateMortalityData data) {
        Optional.ofNullable(person.getNbsEntity().getEntityLocatorParticipations())
            .stream()
            .flatMap(List::stream)
            .filter(elp -> elp.getUseCd().equals("DTH"))
            .findFirst()
            .ifPresentOrElse(
                elp ->
                    // If postalEntityLocator exists with useCd of "DTH", update it
                    ((PostalEntityLocatorParticipation) elp).updateMortalityLocator(asUpdateMortalityLocator(data)),
                () -> {
                    // If postalEntityLocator with useCd of "DTH" does not exist, create it
                    var id = idGenerator.getNextValidId(IdGeneratorService.EntityType.NBS).getId();
                    person.add(asAddMortalityLocator(data, id));
                });

        return personRepository.save(person);
    }

    public Person update(final Person person, final DeleteMortalityData data) {
        person.delete(asDeleteMortalityLocator(data));
        return personRepository.save(person);
    }

    public Person update(Person person, UpdateSexAndBirthData data) {
        person.update(asUpdateSexAndBirthInfo(data));
        return personRepository.save(person);
    }

    private PatientCommand.UpdateMortalityLocator asUpdateMortalityLocator(UpdateMortalityData data) {
        return new PatientCommand.UpdateMortalityLocator(
            data.patientId(),
            data.asOf(),
            data.deceased(),
            data.deceasedTime(),
            data.cityOfDeath(),
            data.stateOfDeath(),
            data.countyOfDeath(),
            data.countryOfDeath(),
            data.updatedBy(),
            Instant.now());
    }

    private PatientCommand.AddMortalityLocator asAddMortalityLocator(UpdateMortalityData data, long id) {
        return new PatientCommand.AddMortalityLocator(
            data.patientId(),
            id,
            data.asOf(),
            data.deceased(),
            data.deceasedTime(),
            data.cityOfDeath(),
            data.stateOfDeath(),
            data.countyOfDeath(),
            data.countryOfDeath(),
            data.updatedBy(),
            Instant.now());
    }

    private PatientCommand.UpdateGeneralInfo asUpdateGeneralInfo(UpdateGeneralInfoData data) {
        return new PatientCommand.UpdateGeneralInfo(
            data.patientId(),
            data.asOf(),
            data.maritalStatus(),
            data.mothersMaidenName(),
            data.adultsInHouseNumber(),
            data.childrenInHouseNumber(),
            data.occupationCode(),
            data.educationLevelCode(),
            data.primaryLanguageCode(),
            data.speaksEnglishCode(),
            data.eharsId(),
            data.updatedBy(),
            Instant.now());
    }

    private PatientCommand.UpdateAdministrativeInfo asUpdateAdministrativeInfo(UpdateAdministrativeData data) {
        return new PatientCommand.UpdateAdministrativeInfo(
            data.patientId(),
            data.asOf(),
            data.description(),
            data.updatedBy(),
            Instant.now());
    }

    private PatientCommand.AddName asAddNameInfo(AddNameData data) {
        return new PatientCommand.AddName(
            data.patientId(),
            data.first(),
            data.middle(),
            data.last(),
            data.suffix(),
            data.type(),
            data.updatedBy(),
            Instant.now());
    }

    private PatientCommand.UpdateNameInfo asUpdateNameInfo(UpdateNameData data) {
        return new PatientCommand.UpdateNameInfo(
            data.patientId(),
            data.asOf(),
            data.personNameSeq(),
            data.first(),
            data.middle(),
            data.last(),
            data.suffix(),
            data.type(),
            data.updatedBy(),
            Instant.now());
    }

    private PatientCommand.DeleteNameInfo asDeleteNameInfo(DeleteNameData data) {
        return new PatientCommand.DeleteNameInfo(
            data.patientId(),
            data.asOf(),
            data.personNameSeq(),
            data.updatedBy(),
            Instant.now());
    }

    private PatientCommand.UpdateSexAndBirthInfo asUpdateSexAndBirthInfo(UpdateSexAndBirthData data) {
        return new PatientCommand.UpdateSexAndBirthInfo(
            data.patientId(),
            data.asOf(),
            data.dateOfBirth(),
            data.birthGender(),
            data.currentGender(),
            data.additionalGender(),
            data.transGenderInfo(),
            data.birthCity(),
            data.birthCntry(),
            data.birthState(),
            data.birthOrderNbr(),
            data.multipleBirth(),
            data.sexUnknown(),
            data.currentAge(),
            data.ageReportedTime(),
            data.updatedBy(),
            Instant.now());
    }

    private PatientCommand.AddAddress asAddAddressInfo(AddAddressData data) {
        return new PatientCommand.AddAddress(
            data.patientId(),
            data.id(),
            data.streetAddress1(),
            data.streetAddress2(),
            new City(data.city()),
            data.stateCode(),
            data.zip(),
            new County(data.countyCode()),
            new Country(data.countryCode()),
            data.censusTract(),
            data.updatedBy(),
            Instant.now());
    }

    private PatientCommand.UpdateAddress asUpdateAddressInfo(UpdateAddressData data) {
        return new PatientCommand.UpdateAddress(
            data.patientId(),
            data.id(),
            data.streetAddress1(),
            data.streetAddress2(),
            new City(data.city()),
            data.stateCode(),
            data.zip(),
            new County(data.countyCode()),
            new Country(data.countryCode()),
            data.censusTract(),
            data.updatedBy(),
            Instant.now());
    }

    private PatientCommand.DeleteAddress asDeleteAddressInfo(DeleteAddressData data) {
        return new PatientCommand.DeleteAddress(
            data.patientId(),
            data.id(),
            data.updatedBy(),
            Instant.now());
    }

    private PatientCommand.AddEmailAddress asAddEmailInfo(AddEmailData data) {
        return new PatientCommand.AddEmailAddress(
            data.patientId(),
            data.id(),
            data.emailAddress(),
            data.updatedBy(),
            Instant.now());
    }

    private PatientCommand.UpdateEmailAddress asUpdateEmailInfo(UpdateEmailData data) {
        return new PatientCommand.UpdateEmailAddress(
            data.patientId(),
            data.id(),
            data.emailAddress(),
            data.updatedBy(),
            Instant.now());
    }

    private PatientCommand.DeleteEmailAddress asDeleteEmailInfo(DeleteEmailData data) {
        return new PatientCommand.DeleteEmailAddress(
            data.patientId(),
            data.id(),
            data.updatedBy(),
            Instant.now());
    }

    private PatientCommand.AddIdentification asAddIdentificationInfo(AddIdentificationData data) {
        return new PatientCommand.AddIdentification(
            data.patientId(),
            data.identificationNumber(),
            data.assigningAuthority(),
            data.identificationType(),
            data.updatedBy(),
            Instant.now());
    }

    private PatientCommand.UpdateIdentification asUpdateIdentificationInfo(UpdateIdentificationData data) {
        return new PatientCommand.UpdateIdentification(
            data.patientId(),
            data.id(),
            data.identificationNumber(),
            data.assigningAuthority(),
            data.identificationType(),
            data.updatedBy(),
            Instant.now());
    }

    private PatientCommand.DeleteIdentification asDeleteIdentificationInfo(DeleteIdentificationData data) {
        return new PatientCommand.DeleteIdentification(
            data.patientId(),
            data.id(),
            data.updatedBy(),
            Instant.now());
    }

    private PatientCommand.AddPhoneNumber asAddPhoneInfo(final AddPhoneData data) {
        return new PatientCommand.AddPhoneNumber(
            data.patientId(),
            data.patientId(),
            data.number(),
            data.extension(),
            data.phoneType().type(),
            data.phoneType().use(),
            data.updatedBy(),
            Instant.now()
        );
    }

    private PatientCommand.UpdatePhoneNumber asUpdatePhoneInfo(UpdatePhoneData data) {
        return new PatientCommand.UpdatePhoneNumber(
            data.patientId(),
            data.id(),
            data.number(),
            data.extension(),
            data.phoneType(),
            data.updatedBy(),
            Instant.now());
    }

    private PatientCommand.DeletePhoneNumber asDeletePhoneInfo(DeletePhoneData data) {
        return new PatientCommand.DeletePhoneNumber(
            data.patientId(),
            data.id(),
            data.updatedBy(),
            Instant.now());
    }

    private PatientCommand.DeleteMortalityLocator asDeleteMortalityLocator(DeleteMortalityData data) {
        return new PatientCommand.DeleteMortalityLocator(
            data.patientId(),
            data.id(),
            data.updatedBy(),
            Instant.now());
    }

}
