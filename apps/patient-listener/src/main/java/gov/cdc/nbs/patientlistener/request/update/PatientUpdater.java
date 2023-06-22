package gov.cdc.nbs.patientlistener.request.update;

import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.message.patient.event.UpdateAdministrativeData;
import gov.cdc.nbs.message.patient.event.UpdateSexAndBirthData;
import gov.cdc.nbs.patient.PatientCommand;
import gov.cdc.nbs.repository.PersonRepository;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class PatientUpdater {
    private final PersonRepository personRepository;

    public PatientUpdater(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Person update(final Person person, final UpdateAdministrativeData data) {
        person.update(asUpdateAdministrativeInfo(data));
        return personRepository.save(person);
    }

    public Person update(Person person, UpdateSexAndBirthData data) {
        person.update(asUpdateSexAndBirthInfo(data));
        return personRepository.save(person);
    }

    private PatientCommand.UpdateAdministrativeInfo asUpdateAdministrativeInfo(UpdateAdministrativeData data) {
        return new PatientCommand.UpdateAdministrativeInfo(
            data.patientId(),
            data.asOf(),
            data.description(),
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
