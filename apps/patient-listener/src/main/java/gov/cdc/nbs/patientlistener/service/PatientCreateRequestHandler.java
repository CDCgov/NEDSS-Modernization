package gov.cdc.nbs.patientlistener.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.message.patient.event.PatientCreateData;
import gov.cdc.nbs.patientlistener.exception.UserNotAuthorizedException;
import gov.cdc.nbs.patientlistener.kafka.StatusProducer;
import gov.cdc.nbs.patientlistener.util.PersonConverter;
import gov.cdc.nbs.repository.elasticsearch.ElasticsearchPersonRepository;
import gov.cdc.nbs.service.UserService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PatientCreateRequestHandler {
    @Autowired
    private UserService userService;

    @Autowired
    private ElasticsearchPersonRepository elasticsearchPersonRepository;

    @Autowired
    private StatusProducer statusProducer;

    @Autowired
    PatientCreator creator;

    @Transactional
    public void handlePatientCreate(PatientCreateData data) {
        long user = data.createdBy();
        log.debug("Attempting to validate permissions for user: {}", user);
        if (userService.isAuthorized(user, "FIND-PATIENT", "ADD-PATIENT")) {
            creationAllowed(data.request(), data);
        } else {
            notAuthorized(data.request());
        }
    }

    private void creationAllowed(final String key, final PatientCreateData createRequest) {
        // perform the creation
        log.debug("User permission validated. Creating patient");
        Person newPatient = createPatient(createRequest);
        createElasticsearchPatient(newPatient);

        // post success message to status topic
        statusProducer.send(true, key, "Successfully created patient", newPatient.getId());
    }

    private void notAuthorized(final String key) {
        log.debug("User lacks permission for patient create");
        throw new UserNotAuthorizedException(key);
    }

    /**
     * Creates a Person entity from the PatientInput and persists it to the database
     */
    private Person createPatient(PatientCreateData request) {
        return creator.create(request);
    }

    /**
     * Converts a Person entity into an ElasticsearchPerson Document and persists it to ES
     */
    private void createElasticsearchPatient(Person person) {
        var esPerson = PersonConverter.toElasticsearchPerson(person);
        elasticsearchPersonRepository.save(esPerson);
    }

}
