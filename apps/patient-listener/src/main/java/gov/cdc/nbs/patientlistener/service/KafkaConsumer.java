package gov.cdc.nbs.patientlistener.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class KafkaConsumer {

    @KafkaListener(topics = "patientSearchTopic", groupId = "group_id")
    public void consume(String message) {
        log.info("message = {}", message);
    }

    @KafkaListener(topics = "patientDeleteTopic", groupId = "group_id")
    public void deleteConsume(String message) {
        log.info("message = {}", message);
    }

    @KafkaListener(topics = "#{'${kafkadef.patient-search.topics.request.patientcreate}'}", groupId = "group_id")
    public void createConsume(String message) {
        log.info("message = {}", message);

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
