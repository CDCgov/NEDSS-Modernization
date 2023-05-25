package gov.cdc.nbs.patientlistener.request.delete;

import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.patient.PatientCommand;
import gov.cdc.nbs.patientlistener.util.PersonConverter;
import gov.cdc.nbs.repository.elasticsearch.ElasticsearchPersonRepository;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.Instant;

@Component
class PatientDeleter {
    private final Clock clock;
    private final ElasticsearchPersonRepository searchRepository;

    PatientDeleter(
        final Clock clock,
        final ElasticsearchPersonRepository searchRepository
    ) {

        this.clock = clock;
        this.searchRepository = searchRepository;
    }

    Person delete(final Person person, final long userId) {
        person.delete(asDelete(person.getId(), userId));
        deleteElasticsearchPatient(person);

        return person;
    }

    private PatientCommand.Delete asDelete(long patientId, long userId) {
        return new PatientCommand.Delete(patientId, userId, Instant.now(clock));
    }

    /*
     * Converts the 'deleted' Person to an ElasticsearchPerson and saves to ES. This will overwrite the existing entry
     */
    private void deleteElasticsearchPatient(final Person person) {
        var esPerson = PersonConverter.toElasticsearchPerson(person);
        searchRepository.save(esPerson);
    }
}
