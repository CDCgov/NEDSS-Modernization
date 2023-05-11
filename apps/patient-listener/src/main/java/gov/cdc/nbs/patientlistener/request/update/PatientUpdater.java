package gov.cdc.nbs.patientlistener.request.update;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;
import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.entity.odse.PostalEntityLocatorParticipation;
import gov.cdc.nbs.message.patient.event.AddNameData;
import gov.cdc.nbs.message.patient.event.DeleteNameData;
import gov.cdc.nbs.message.patient.event.UpdateAdministrativeData;
import gov.cdc.nbs.message.patient.event.UpdateGeneralInfoData;
import gov.cdc.nbs.message.patient.event.UpdateMortalityData;
import gov.cdc.nbs.message.patient.event.UpdateNameData;
import gov.cdc.nbs.message.patient.event.UpdateSexAndBirthData;
import gov.cdc.nbs.patient.IdGeneratorService;
import gov.cdc.nbs.patient.PatientCommand;
import gov.cdc.nbs.repository.PersonRepository;

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
}
