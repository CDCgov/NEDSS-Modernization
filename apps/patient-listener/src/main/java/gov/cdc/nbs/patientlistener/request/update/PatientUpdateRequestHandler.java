package gov.cdc.nbs.patientlistener.request.update;

import gov.cdc.nbs.authentication.UserService;
import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.message.patient.event.AddNameData;
import gov.cdc.nbs.message.patient.event.DeleteNameData;
import gov.cdc.nbs.message.patient.event.UpdateAdministrativeData;
import gov.cdc.nbs.message.patient.event.UpdateGeneralInfoData;
import gov.cdc.nbs.message.patient.event.UpdateMortalityData;
import gov.cdc.nbs.message.patient.event.UpdateNameData;
import gov.cdc.nbs.message.patient.event.UpdateSexAndBirthData;
import gov.cdc.nbs.patientlistener.request.PatientNotFoundException;
import gov.cdc.nbs.patientlistener.request.UserNotAuthorizedException;
import gov.cdc.nbs.patientlistener.request.PatientRequestStatusProducer;
import gov.cdc.nbs.patientlistener.util.PersonConverter;
import gov.cdc.nbs.repository.PersonRepository;
import gov.cdc.nbs.repository.elasticsearch.ElasticsearchPersonRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PatientUpdateRequestHandler {
    private final PersonRepository personRepository;
    private final UserService userService;
    private final PatientUpdater patientUpdater;
    private final PatientRequestStatusProducer statusProducer;
    private final ElasticsearchPersonRepository elasticsearchPersonRepository;


    public PatientUpdateRequestHandler(PersonRepository personRepository,
            UserService userService,
            PatientUpdater patientUpdater,
            PatientRequestStatusProducer statusProducer,
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
    @Transactional
    public void handlePatientGeneralInfoUpdate(final UpdateGeneralInfoData data) {
        if (!userService.isAuthorized(data.updatedBy(), VIEW_PATIENT, EDIT_PATIENT)) {
            throw new UserNotAuthorizedException(data.requestId());
        }

        var person = findPerson(data.patientId(), data.requestId());
        person = patientUpdater.update(person, data);
        updateElasticsearchPatient(person);
        statusProducer.successful(
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
    @Transactional
    public void handlePatientMortalityUpdate(final UpdateMortalityData data) {
        if (!userService.isAuthorized(data.updatedBy(), VIEW_PATIENT, EDIT_PATIENT)) {
            throw new UserNotAuthorizedException(data.requestId());
        }

        var person = findPerson(data.patientId(), data.requestId());
        patientUpdater.update(person, data);
        updateElasticsearchPatient(person);
        statusProducer.successful(
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
    @Transactional
    public void handlePatientSexAndBirthUpdate(final UpdateSexAndBirthData data) {
        if (!userService.isAuthorized(data.updatedBy(), VIEW_PATIENT, EDIT_PATIENT)) {
            throw new UserNotAuthorizedException(data.requestId());
        }

        var person = findPerson(data.patientId(), data.requestId());
        patientUpdater.update(person, data);
        updateElasticsearchPatient(person);
        statusProducer.successful(
                data.requestId(),
                "Successfully updated patient sex and birth info",
                data.patientId());
    }

    /**
     * Sets the Person's administrative info to the specified values.
     *
     * @param data
     */
    @Transactional
    public void handlePatientAdministrativeUpdate(final UpdateAdministrativeData data) {
        if (!userService.isAuthorized(data.updatedBy(), VIEW_PATIENT, EDIT_PATIENT)) {
            throw new UserNotAuthorizedException(data.requestId());
        }

        var person = findPerson(data.patientId(), data.requestId());
        patientUpdater.update(person, data);
        updateElasticsearchPatient(person);
        statusProducer.successful(
                data.requestId(),
                "Successfully updated administrative info",
                data.patientId());
    }

    /**
     * Add a person name.
     *
     * @param data
     */
    @Transactional
    public void handlePatientNameAdd(final AddNameData data) {
        if (!userService.isAuthorized(data.updatedBy(), VIEW_PATIENT, EDIT_PATIENT)) {
            throw new UserNotAuthorizedException(data.requestId());
        }

        var person = findPerson(data.patientId(), data.requestId());
        patientUpdater.update(person, data);
        updateElasticsearchPatient(person);
        statusProducer.successful(
                data.requestId(),
                "Successfully add a patient name",
                data.patientId());
    }

    /**
     * Update a person name.
     *
     * @param data
     */
    @Transactional
    public void handlePatientNameUpdate(final UpdateNameData data) {
        if (!userService.isAuthorized(data.updatedBy(), VIEW_PATIENT, EDIT_PATIENT)) {
            throw new UserNotAuthorizedException(data.requestId());
        }

        var person = findPerson(data.patientId(), data.requestId());
        patientUpdater.update(person, data);
        updateElasticsearchPatient(person);
        statusProducer.successful(
                data.requestId(),
                "Successfully updated patient name",
                data.patientId());
    }

    /**
     * Delete a person name.
     *
     * @param data
     */
    @Transactional
    public void handlePatientNameDelete(final String requestId, final long patientId, short personNameSeq,
            final long userId) {
        if (!userService.isAuthorized(userId, VIEW_PATIENT, EDIT_PATIENT)) {
            throw new UserNotAuthorizedException(requestId);
        }
        var person = findPerson(patientId, requestId);
        patientUpdater.update(person, new DeleteNameData(patientId, personNameSeq, requestId, userId, null));
        updateElasticsearchPatient(person);
        statusProducer.successful(
                requestId,
                "Successfully deleted patient name",
                userId);
    }

    private Person findPerson(final long patientId, final String requestId) {
        var optional = personRepository.findById(patientId);
        if (optional.isEmpty()) {
            throw new PatientNotFoundException(patientId, requestId);
        }
        return optional.get();
    }

    private void updateElasticsearchPatient(final Person person) {
        var esPerson = PersonConverter.toElasticsearchPerson(person);
        elasticsearchPersonRepository.save(esPerson);
    }

}
