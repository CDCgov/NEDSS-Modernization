package gov.cdc.nbs.patientlistener.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import gov.cdc.nbs.patientlistener.service.PatientCreateRequestHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class KafkaConsumer {

    @Autowired
    private PatientCreateRequestHandler patientService;

    @KafkaListener(topics = "patientSearchTopic", groupId = "group_id")
    public void consume(String message) {
        log.info("message = {}", message);
    }

    @KafkaListener(topics = "patientDeleteTopic", groupId = "group_id")
    public void deleteConsume(String message) {
        log.info("message = {}", message);
    }

    @Transactional
    @KafkaListener(topics = "#{'${kafkadef.patient-search.topics.request.patient-create}'}", groupId = "group_id")
    public void createConsume(String message, @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key) {
        log.debug("Receieved patientcreate message: '{}'", message);
        patientService.handlePatientCreate(message, key);
    }
}
