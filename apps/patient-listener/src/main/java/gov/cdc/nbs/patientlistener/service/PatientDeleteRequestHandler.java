package gov.cdc.nbs.patientlistener.service;

import org.springframework.stereotype.Service;
import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.patientlistener.kafka.StatusProducer;
import gov.cdc.nbs.patientlistener.util.PersonConverter;
import gov.cdc.nbs.repository.PersonRepository;
import gov.cdc.nbs.repository.elasticsearch.ElasticsearchPersonRepository;
import gov.cdc.nbs.service.UserService;

@Service
public class PatientDeleteRequestHandler {
    private final UserService userService;
    private final PatientDeleter patientDeleter;
    private final StatusProducer statusProducer;
    private final PersonRepository personRepository;
    private final ElasticsearchPersonRepository elasticsearchPersonRepository;

    public PatientDeleteRequestHandler(
            UserService userService,
            PatientDeleter patientDeleter,
            StatusProducer statusProducer,
            PersonRepository personRepository,
            ElasticsearchPersonRepository elasticsearchPersonRepository) {
        this.userService = userService;
        this.patientDeleter = patientDeleter;
        this.statusProducer = statusProducer;
        this.personRepository = personRepository;
        this.elasticsearchPersonRepository = elasticsearchPersonRepository;
    }

    /*
     * Sets a patients RecordStatus to 'LOG_DEL'
     */
    public void handlePatientDelete(final String requestId, final long patientId, final long userId) {
        if (!userService.isAuthorized(userId, "VIEW-PATIENT", "DELETE-PATIENT")) {
            statusProducer.send(false, requestId, "User not authorized to perform this operation");
            return;
        }
        var optional = personRepository.findById(patientId);
        if (optional.isEmpty()) {
            statusProducer.send(
                    false,
                    requestId,
                    "Failed to find patient in database: " + patientId);
            return;
        }

        var person = optional.get();
        person = patientDeleter.delete(person, userId);
        deleteElasticsearchPatient(person);
        statusProducer.send(true, requestId, "Successfully deleted patient", patientId);
    }

    /*
     * Converts the 'deleted' Person to an ElasticsearchPerson and saves to ES. This will overwrite the existing entry
     */
    private void deleteElasticsearchPatient(Person person) {
        var esPerson = PersonConverter.toElasticsearchPerson(person);
        elasticsearchPersonRepository.save(esPerson);
    }



}
