package gov.cdc.nbs.patientlistener.service;

import java.time.Instant;
import org.springframework.stereotype.Component;
import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.patient.PatientCommand;
import gov.cdc.nbs.repository.PersonRepository;

@Component
public class PatientDeleter {
    private final PersonRepository personRepository;

    public PatientDeleter(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Person delete(final Person person, final long userId) {
        // TODO check for active revisions
        person.delete(asDelete(person.getId(), userId));
        return personRepository.save(person);
    }

    private PatientCommand.Delete asDelete(long patientId, long userId) {
        return new PatientCommand.Delete(patientId, userId, Instant.now());
    }
}
