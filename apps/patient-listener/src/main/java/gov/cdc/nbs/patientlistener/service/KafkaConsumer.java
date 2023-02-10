package gov.cdc.nbs.patientlistener.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import gov.cdc.nbs.config.security.NbsAuthority;
import gov.cdc.nbs.message.PatientCreateRequest;
import gov.cdc.nbs.service.UserService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class KafkaConsumer {

    @Autowired
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @KafkaListener(topics = "patientSearchTopic", groupId = "group_id")
    public void consume(String message) {
        log.info("message = {}", message);
    }

    @KafkaListener(topics = "patientDeleteTopic", groupId = "group_id")
    public void deleteConsume(String message) {
        log.info("message = {}", message);
    }

    @Transactional
    @KafkaListener(topics = "#{'${kafkadef.patient-search.topics.request.patientcreate}'}", groupId = "group_id")
    public void createConsume(String message) {
        log.debug("Receieved patientcreate message: '{}'", message);
        // convert message to Object
        try {
            var createRequest = objectMapper.readValue(message, PatientCreateRequest.class);
            var userId = createRequest.getUserId();
            // validate user has ADD-PATIENT and FIND-PATIENT permissions
            try {
                var userDetails = userService.loadUserByUsername(userId);
                var userPermissions = userDetails.getAuthorities().stream().map(NbsAuthority::getAuthority).toList();
                if (userPermissions.contains("FIND-PATIENT") && userPermissions.contains("ADD-PATIENT")) {
                    // user has permission. performt the creation
                    log.info("User permission validated. Performing patient create");
                } else {
                    // user does not have permission
                    log.info("User lacks permission for patient create");
                }

            } catch (Exception e) {
                log.warn("Failed to find user credentials for userId: {}", userId);
            }
            // create the patient
        } catch (JsonProcessingException e) {
            log.warn("Failed to map message to PatientCreateRequest object. Message: '{}'", message);
        }

        // final long id = personRepository.getMaxId() + 1;
        // var person = new Person();
        // // generated / required values
        // person.setId(id);
        // person.setNbsEntity(new NBSEntity(id, "PSN"));
        // person.setVersionCtrlNbr((short) 1);
        // person.setAddTime(Instant.now());
        // person.setRecordStatusCd(RecordStatus.ACTIVE);

        // // person table
        // if (input.getName() != null) {
        // person.setLastNm(input.getName().getLastName());
        // person.setFirstNm(input.getName().getFirstName());
        // person.setMiddleNm(input.getName().getMiddleName());
        // person.setNmSuffix(input.getName().getSuffix());
        // }
        // person.setSsn(input.getSsn());
        // person.setBirthTime(input.getDateOfBirth());
        // person.setBirthGenderCd(input.getBirthGender());
        // person.setCurrSexCd(input.getCurrentGender());
        // person.setDeceasedIndCd(input.getDeceased());
        // person.setEthnicGroupInd(input.getEthnicity());

        // // person_name
        // addPersonNameEntry(person, input.getName());

        // // person_race
        // addPersonRaceEntry(person, input.getRace());

        // // tele_locator
        // var teleLocators = addTeleLocatorEntries(person, input.getPhoneNumbers(),
        // input.getEmailAddresses());

        // // postal_locator
        // var postalLocators = addPostalLocatorEntries(person, input.getAddresses());

        // // Save
        // teleLocatorRepository.saveAll(teleLocators);
        // postalLocatorRepository.saveAll(postalLocators);
        // return personRepository.save(person);
    }
}
