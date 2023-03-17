package gov.cdc.nbs.patientlistener.service;

import java.time.Instant;
import org.springframework.stereotype.Component;
import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.entity.odse.PostalEntityLocatorParticipation;
import gov.cdc.nbs.message.patient.event.UpdateGeneralInfoData;
import gov.cdc.nbs.message.patient.event.UpdateMortalityData;
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

    public Person update(final Person person, final UpdateMortalityData data) {
        var mortalityElp = person.getNbsEntity()
                .getEntityLocatorParticipations()
                .stream()
                .filter(elp -> elp.getUseCd().equals("DTH"))
                .findFirst();
        if (mortalityElp.isPresent()) {
            var elp = (PostalEntityLocatorParticipation) mortalityElp.get();
            elp.updateMortalityLocator(asUpdateMortalityLocator(data));
            return personRepository.save(person);
        } else {
            var id = idGenerator.getNextValidId(IdGeneratorService.EntityType.NBS).getId();
            person.add(asAddMortalityLocator(data, id));
            return personRepository.save(person);
        }
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
}
