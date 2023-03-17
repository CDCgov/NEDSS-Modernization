package gov.cdc.nbs.patientlistener.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.message.RequestStatus;
import gov.cdc.nbs.message.patient.event.UpdateGeneralInfoData;
import gov.cdc.nbs.message.patient.event.UpdateMortalityData;
import gov.cdc.nbs.message.patient.event.UpdateSexAndBirthData;
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
    private final KafkaTemplate<String, RequestStatus> statusTemplate;
    private final ElasticsearchPersonRepository elasticPersonRepository;


    public PatientUpdateRequestHandler(PersonRepository personRepository, UserService userService,
            PatientUpdater patientUpdater, KafkaTemplate<String, RequestStatus> statusTemplate,
            ElasticsearchPersonRepository elasticPersonRepository, String statusTopic) {
        this.personRepository = personRepository;
        this.userService = userService;
        this.patientUpdater = patientUpdater;
        this.statusTemplate = statusTemplate;
        this.elasticPersonRepository = elasticPersonRepository;
        this.statusTopic = statusTopic;
    }

    @Value("${kafkadef.topics.status.patient}")
    private String statusTopic;

    private static final String VIEW_PATIENT = "VIEW-PATIENT";
    private static final String EDIT_PATIENT = "EDIT-PATIENT";

    public void handlePatientGeneralInfoUpdate(final UpdateGeneralInfoData data) {
        if (userService.isAuthorized(data.updatedBy(), VIEW_PATIENT, EDIT_PATIENT)) {
            var optional = personRepository.findById(data.patientId());
            if (optional.isEmpty()) {
                sendPatientCreateStatus(false, data.requestId(),
                        "Failed to find patient in database: " + data.patientId());
                return;
            }

            var person = patientUpdater.update(optional.get(), data);
            updateElasticsearchPatient(person);
            sendPatientCreateStatus(
                    true,
                    data.requestId(),
                    "Successfully updated patient",
                    data.patientId());

        } else {
            notAuthorized(data.requestId());
        }
    }

    public void handlePatientMortalityUpdate(final UpdateMortalityData data) {
        if (userService.isAuthorized(data.updatedBy(), VIEW_PATIENT, EDIT_PATIENT)) {
            var optionalPerson = personRepository.findById(data.patientId());
            if (optionalPerson.isEmpty()) {
                sendPatientCreateStatus(
                        false,
                        data.requestId(),
                        "Failed to find patient in database: " + data.patientId());
                return;
            }
            var person = patientUpdater.update(optionalPerson.get(), data);
            updateElasticsearchPatient(person);
            sendPatientCreateStatus(
                    true,
                    data.requestId(),
                    "Successfully updated patient mortality info",
                    data.patientId());
        } else {
            notAuthorized(data.requestId());
        }
    }


    public void handlePatientSexAndBirthUpdate(final UpdateSexAndBirthData data) {
        if (userService.isAuthorized(data.updatedBy(), VIEW_PATIENT, EDIT_PATIENT)) {
            var optionalPerson = personRepository.findById(data.patientId());
            if (optionalPerson.isEmpty()) {
                sendPatientCreateStatus(
                        false,
                        data.requestId(),
                        "Failed to find patient in database: " + data.patientId());
                return;
            }
        }
        // TODO fields:
        // oldPerson.setBirthGenderCd(
        //         input.birthGender() != null ? input.birthGender() : oldPerson.getBirthGenderCd());
        // oldPerson.setCurrSexCd(input.currentGender() != null ? input.currentGender() : oldPerson.getCurrSexCd());
        // oldPerson.setBirthTime(input.dateOfBirth() != null ? input.dateOfBirth() : oldPerson.getBirthTime());
        // oldPerson.setAsOfDateSex(input.asOf() != null ? input.asOf() : oldPerson.getAsOfDateSex());
        // oldPerson.setAgeReported(input.currentAge() != null ? input.currentAge() : oldPerson.getAgeReported());
        // oldPerson.setAgeReportedTime(
        //         input.ageReportedTime() != null ? input.ageReportedTime() : oldPerson.getAgeReportedTime());
        // oldPerson.setBirthCityCd(input.birthCity() != null ? input.birthCity() : oldPerson.getBirthCityCd());
        // oldPerson.setBirthCntryCd(input.birthCntry() != null ? input.birthCntry() : oldPerson.getBirthCntryCd());
        // oldPerson.setBirthStateCd(input.birthState() != null ? input.birthState() : oldPerson.getBirthStateCd());
        // oldPerson
        //         .setBirthOrderNbr(input.birthOrderNbr() != null ? input.birthOrderNbr() : oldPerson.getBirthOrderNbr());
        // oldPerson.setMultipleBirthInd(
        //         input.multipleBirth() != null ? input.multipleBirth() : oldPerson.getMultipleBirthInd());
        // oldPerson.setSexUnkReasonCd(input.sexunknown() != null ? input.sexunknown() : oldPerson.getSexUnkReasonCd());
        // oldPerson.setAdditionalGenderCd(input.additionalGender() != null ? input.additionalGender().toString()
        //         : oldPerson.getAdditionalGenderCd());
        // oldPerson.setPreferredGenderCd(input.transGenderInfo() != null ? input.transGenderInfo().toString()
        //         : oldPerson.getPreferredGenderCd());
    }

    private void updateElasticsearchPatient(final Person person) {
        System.out.println(elasticPersonRepository);
    }


    private void notAuthorized(final String key) {
        log.debug("User lacks permission for patient create");
        sendPatientCreateStatus(false, key, "User not authorized to perform this operation");
    }

    private void sendPatientCreateStatus(boolean successful, String key, String message) {
        sendPatientCreateStatus(successful, key, message, null);
    }

    private void sendPatientCreateStatus(boolean successful, String key, String message, Long entityId) {
        var status = RequestStatus.builder()
                .successful(successful)
                .requestId(key)
                .message(message)
                .entityId(entityId)
                .build();
        statusTemplate.send(statusTopic, status);
    }

}
