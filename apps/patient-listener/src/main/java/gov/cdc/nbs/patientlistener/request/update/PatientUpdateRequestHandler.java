package gov.cdc.nbs.patientlistener.request.update;

import gov.cdc.nbs.authentication.UserService;
import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.message.patient.event.AddNameData;
import gov.cdc.nbs.message.patient.event.AddRaceData;
import gov.cdc.nbs.message.patient.event.DeleteNameData;
import gov.cdc.nbs.message.patient.event.UpdateAdministrativeData;
import gov.cdc.nbs.message.patient.event.UpdateGeneralInfoData;
import gov.cdc.nbs.message.patient.event.UpdateMortalityData;
import gov.cdc.nbs.message.patient.event.UpdateNameData;
import gov.cdc.nbs.message.patient.event.UpdateSexAndBirthData;
import gov.cdc.nbs.message.patient.event.UpdateEthnicityData;
import gov.cdc.nbs.message.patient.event.UpdateRaceData;
import gov.cdc.nbs.message.patient.event.UpdateAddressData;
import gov.cdc.nbs.message.patient.event.DeleteAddressData;
import gov.cdc.nbs.message.patient.event.UpdateEmailData;
import gov.cdc.nbs.message.patient.event.DeleteEmailData;
import gov.cdc.nbs.message.patient.event.UpdateIdentificationData;
import gov.cdc.nbs.message.patient.event.DeleteIdentificationData;
import gov.cdc.nbs.message.patient.event.UpdatePhoneData;
import gov.cdc.nbs.message.patient.event.DeletePhoneData;
import gov.cdc.nbs.message.patient.event.DeleteRaceData;
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

  /**
   * Sets the Person's administrative info to the specified values.
   *
   * @param data
   */
  @Transactional
  public void handlePatientEthnicityUpdate(final UpdateEthnicityData data) {
    if (!userService.isAuthorized(data.updatedBy(), VIEW_PATIENT, EDIT_PATIENT)) {
      throw new UserNotAuthorizedException(data.requestId());
    }

    var person = findPerson(data.patientId(), data.requestId());
    patientUpdater.update(person, data);
    updateElasticsearchPatient(person);
    statusProducer.successful(
        data.requestId(),
        "Successfully updated Ethnicity info",
        data.patientId());
  }

  /**
   * Sets the Person's Race info to the specified values.
   *
   * @param data
   */
  @Transactional
  public void handlePatientRaceAdd(final AddRaceData data) {
    if (!userService.isAuthorized(data.updatedBy(), VIEW_PATIENT, EDIT_PATIENT)) {
      throw new UserNotAuthorizedException(data.requestId());
    }

    var person = findPerson(data.patientId(), data.requestId());
    patientUpdater.update(person, data);
    updateElasticsearchPatient(person);
    statusProducer.successful(
        data.requestId(),
        "Successfully updated Race info",
        data.patientId());
  }

  /**
   * Sets the Person's Race info to the specified values.
   *
   * @param data
   */
  @Transactional
  public void handlePatientRaceUpdate(final UpdateRaceData data) {
    if (!userService.isAuthorized(data.updatedBy(), VIEW_PATIENT, EDIT_PATIENT)) {
      throw new UserNotAuthorizedException(data.requestId());
    }

    var person = findPerson(data.patientId(), data.requestId());
    patientUpdater.update(person, data);
    updateElasticsearchPatient(person);
    statusProducer.successful(
        data.requestId(),
        "Successfully updated Race info",
        data.patientId());
  }

  /**
   * Delete a person Race.
   *
   * @param data
   */
  @Transactional
  public void handlePatientRaceDelete(final String requestId, final long patientId, String raceCd, final long userId) {
    if (!userService.isAuthorized(userId, VIEW_PATIENT, EDIT_PATIENT)) {
      throw new UserNotAuthorizedException(requestId);
    }
    var person = findPerson(patientId, requestId);
    patientUpdater.update(person, new DeleteRaceData(patientId, requestId, userId, null, raceCd));
    updateElasticsearchPatient(person);
    statusProducer.successful(
        requestId,
        "Successfully deleted patient Race",
        userId);
  }

  /**
   * Add a person Address.
   *
   * @param data
   */
  @Transactional
  public void handlePatientAddressAdd(final UpdateAddressData data) {
    if (!userService.isAuthorized(data.updatedBy(), VIEW_PATIENT, EDIT_PATIENT)) {
      throw new UserNotAuthorizedException(data.requestId());
    }

    var person = findPerson(data.patientId(), data.requestId());
    patientUpdater.update(person, data);
    updateElasticsearchPatient(person);
    statusProducer.successful(
        data.requestId(),
        "Successfully add a patient Address",
        data.patientId());
  }

  /**
   * Update a person Address.
   *
   * @param data
   */
  @Transactional
  public void handlePatientAddressUpdate(final UpdateAddressData data) {
    if (!userService.isAuthorized(data.updatedBy(), VIEW_PATIENT, EDIT_PATIENT)) {
      throw new UserNotAuthorizedException(data.requestId());
    }

    var person = findPerson(data.patientId(), data.requestId());
    patientUpdater.update(person, data);
    updateElasticsearchPatient(person);
    statusProducer.successful(
        data.requestId(),
        "Successfully updated patient Address",
        data.patientId());
  }

  /**
   * Delete a person Address.
   *
   * @param data
   */
  @Transactional
  public void handlePatientAddressDelete(final String requestId, final long patientId, long id, final long userId) {
    if (!userService.isAuthorized(userId, VIEW_PATIENT, EDIT_PATIENT)) {
      throw new UserNotAuthorizedException(requestId);
    }
    var person = findPerson(patientId, requestId);
    patientUpdater.update(person, new DeleteAddressData(patientId, id, requestId, userId, null));
    updateElasticsearchPatient(person);
    statusProducer.successful(
        requestId,
        "Successfully deleted patient Address",
        userId);
  }

  /**
   * Add a person Email.
   *
   * @param data
   */
  @Transactional
  public void handlePatientEmailAdd(final UpdateEmailData data) {
    if (!userService.isAuthorized(data.updatedBy(), VIEW_PATIENT, EDIT_PATIENT)) {
      throw new UserNotAuthorizedException(data.requestId());
    }

    var person = findPerson(data.patientId(), data.requestId());
    patientUpdater.update(person, data);
    updateElasticsearchPatient(person);
    statusProducer.successful(
        data.requestId(),
        "Successfully add a patient Email",
        data.patientId());
  }

  /**
   * Update a person Email.
   *
   * @param data
   */
  @Transactional
  public void handlePatientEmailUpdate(final UpdateEmailData data) {
    if (!userService.isAuthorized(data.updatedBy(), VIEW_PATIENT, EDIT_PATIENT)) {
      throw new UserNotAuthorizedException(data.requestId());
    }

    var person = findPerson(data.patientId(), data.requestId());
    patientUpdater.update(person, data);
    updateElasticsearchPatient(person);
    statusProducer.successful(
        data.requestId(),
        "Successfully updated patient Email",
        data.patientId());
  }

  /**
   * Delete a person Email.
   *
   * @param data
   */
  @Transactional
  public void handlePatientEmailDelete(final String requestId, final long patientId, long id, final long userId) {
    if (!userService.isAuthorized(userId, VIEW_PATIENT, EDIT_PATIENT)) {
      throw new UserNotAuthorizedException(requestId);
    }
    var person = findPerson(patientId, requestId);
    patientUpdater.update(person, new DeleteEmailData(patientId, id, requestId, userId, null));
    updateElasticsearchPatient(person);
    statusProducer.successful(
        requestId,
        "Successfully deleted patient Email",
        userId);
  }

  /**
   * Add a person Phone.
   *
   * @param data
   */
  @Transactional
  public void handlePatientPhoneAdd(final UpdatePhoneData data) {
    if (!userService.isAuthorized(data.updatedBy(), VIEW_PATIENT, EDIT_PATIENT)) {
      throw new UserNotAuthorizedException(data.requestId());
    }

    var person = findPerson(data.patientId(), data.requestId());
    patientUpdater.update(person, data);
    updateElasticsearchPatient(person);
    statusProducer.successful(
        data.requestId(),
        "Successfully add a patient Phone",
        data.patientId());
  }

  /**
   * Update a person Phone.
   *
   * @param data
   */
  @Transactional
  public void handlePatientPhoneUpdate(final UpdatePhoneData data) {
    if (!userService.isAuthorized(data.updatedBy(), VIEW_PATIENT, EDIT_PATIENT)) {
      throw new UserNotAuthorizedException(data.requestId());
    }

    var person = findPerson(data.patientId(), data.requestId());
    patientUpdater.update(person, data);
    updateElasticsearchPatient(person);
    statusProducer.successful(
        data.requestId(),
        "Successfully updated patient Phone",
        data.patientId());
  }

  /**
   * Delete a person Phone.
   *
   * @param data
   */
  @Transactional
  public void handlePatientPhoneDelete(final String requestId, final long patientId, long id, final long userId) {
    if (!userService.isAuthorized(userId, VIEW_PATIENT, EDIT_PATIENT)) {
      throw new UserNotAuthorizedException(requestId);
    }
    var person = findPerson(patientId, requestId);
    patientUpdater.update(person, new DeletePhoneData(patientId, id, requestId, userId, null));
    updateElasticsearchPatient(person);
    statusProducer.successful(
        requestId,
        "Successfully deleted patient Phone",
        userId);
  }

  /**
   * Add a person Phone.
   *
   * @param data
   */
  @Transactional
  public void handlePatientIdentificationAdd(final UpdateIdentificationData data) {
    if (!userService.isAuthorized(data.updatedBy(), VIEW_PATIENT, EDIT_PATIENT)) {
      throw new UserNotAuthorizedException(data.requestId());
    }

    var person = findPerson(data.patientId(), data.requestId());
    patientUpdater.update(person, data);
    updateElasticsearchPatient(person);
    statusProducer.successful(
        data.requestId(),
        "Successfully add a patient Identification",
        data.patientId());
  }

  /**
   * Update a person Identification.
   *
   * @param data
   */
  @Transactional
  public void handlePatientIdentificationUpdate(final UpdateIdentificationData data) {
    if (!userService.isAuthorized(data.updatedBy(), VIEW_PATIENT, EDIT_PATIENT)) {
      throw new UserNotAuthorizedException(data.requestId());
    }

    var person = findPerson(data.patientId(), data.requestId());
    patientUpdater.update(person, data);
    updateElasticsearchPatient(person);
    statusProducer.successful(
        data.requestId(),
        "Successfully updated patient Identification",
        data.patientId());
  }

  /**
   * Delete a person Identification.
   *
   * @param data
   */
  @Transactional
  public void handlePatientIdentificationDelete(final String requestId, final long patientId, short id,
      final long userId) {
    if (!userService.isAuthorized(userId, VIEW_PATIENT, EDIT_PATIENT)) {
      throw new UserNotAuthorizedException(requestId);
    }
    var person = findPerson(patientId, requestId);
    patientUpdater.update(person, new DeleteIdentificationData(patientId, id, requestId, userId, null));
    updateElasticsearchPatient(person);
    statusProducer.successful(
        requestId,
        "Successfully deleted patient Identification",
        userId);
  }

}
