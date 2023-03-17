package gov.cdc.nbs.patientlistener.service;

import org.springframework.stereotype.Service;
import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.message.patient.event.UpdateGeneralInfoData;
import gov.cdc.nbs.message.patient.event.UpdateMortalityData;
import gov.cdc.nbs.message.patient.event.UpdateSexAndBirthData;
import gov.cdc.nbs.patientlistener.kafka.StatusProducer;
import gov.cdc.nbs.patientlistener.util.PersonConverter;
import gov.cdc.nbs.repository.PersonRepository;
import gov.cdc.nbs.repository.elasticsearch.ElasticsearchPersonRepository;
import gov.cdc.nbs.service.UserService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PatientUpdateRequestHandler {
    private final PersonRepository personRepository;
    private final UserService userService;
    private final PatientUpdater patientUpdater;
    private final StatusProducer statusProducer;
    private final ElasticsearchPersonRepository elasticsearchPersonRepository;


    public PatientUpdateRequestHandler(PersonRepository personRepository,
            UserService userService,
            PatientUpdater patientUpdater,
            StatusProducer statusProducer,
            ElasticsearchPersonRepository elasticsearchPersonRepository) {
        this.personRepository = personRepository;
        this.userService = userService;
        this.patientUpdater = patientUpdater;
        this.statusProducer = statusProducer;
        this.elasticsearchPersonRepository = elasticsearchPersonRepository;
    }

    private static final String VIEW_PATIENT = "VIEW-PATIENT";
    private static final String EDIT_PATIENT = "EDIT-PATIENT";

    /**
     * Sets the Person's general info to the specified values. See
     * {@link gov.cdc.nbs.message.patient.event.UpdateGeneralInfoData} for a list of general info fields
     * 
     * @param data
     */
    public void handlePatientGeneralInfoUpdate(final UpdateGeneralInfoData data) {
        if (!userService.isAuthorized(data.updatedBy(), VIEW_PATIENT, EDIT_PATIENT)) {
            notAuthorized(data.requestId());
        }
        var optional = personRepository.findById(data.patientId());
        if (optional.isEmpty()) {
            sendPatientDoesntExistStatus(data.requestId(), data.patientId());
            return;
        }

        var person = patientUpdater.update(optional.get(), data);
        updateElasticsearchPatient(person);
        statusProducer.send(
                true,
                data.requestId(),
                "Successfully updated patient",
                data.patientId());
    }

    /**
     * Sets the Person's mortality info to the specified values. See
     * {@link gov.cdc.nbs.message.patient.event.UpdateMortalityData} for a list of mortality fields
     * 
     * @param data
     */
    public void handlePatientMortalityUpdate(final UpdateMortalityData data) {
        if (!userService.isAuthorized(data.updatedBy(), VIEW_PATIENT, EDIT_PATIENT)) {
            notAuthorized(data.requestId());
        }
        var optionalPerson = personRepository.findById(data.patientId());
        if (optionalPerson.isEmpty()) {
            sendPatientDoesntExistStatus(data.requestId(), data.patientId());
            return;
        }

        var person = patientUpdater.update(optionalPerson.get(), data);
        updateElasticsearchPatient(person);
        statusProducer.send(
                true,
                data.requestId(),
                "Successfully updated patient mortality info",
                data.patientId());
    }

    /**
     * Sets the Person's sex and birth info to the specified values. See
     * {@link gov.cdc.nbs.message.patient.event.UpdateSexAndBirthData} for a list of sex and birth fields
     * 
     * @param data
     */
    public void handlePatientSexAndBirthUpdate(final UpdateSexAndBirthData data) {
        if (!userService.isAuthorized(data.updatedBy(), VIEW_PATIENT, EDIT_PATIENT)) {
            notAuthorized(data.requestId());
        }
        var optionalPerson = personRepository.findById(data.patientId());
        if (optionalPerson.isEmpty()) {
            sendPatientDoesntExistStatus(data.requestId(), data.patientId());
            return;
        }

        var person = patientUpdater.update(optionalPerson.get(), data);
        updateElasticsearchPatient(person);
        statusProducer.send(
                true,
                data.requestId(),
                "Successfully updated patient sex and birth info",
                data.patientId());
    }


    private void updateElasticsearchPatient(final Person person) {
        var esPerson = PersonConverter.toElasticsearchPerson(person);
        elasticsearchPersonRepository.save(esPerson);
    }


    private void notAuthorized(final String key) {
        log.debug("User lacks permission for patient create");
        statusProducer.send(false, key, "User not authorized to perform this operation");
    }

    private void sendPatientDoesntExistStatus(String requestId, long patientId) {
        statusProducer.send(
                false,
                requestId,
                "Failed to find patient in database: " + patientId);
    }

}
