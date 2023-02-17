package gov.cdc.nbs.patientlistener.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import gov.cdc.nbs.graphql.input.PatientInput;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class KafkaConsumer {

    @KafkaListener(topics = "patientSearchTopic", groupId = "group_id")
    public void consume(String message) {
        log.info("message = {}", message);
    }

    @KafkaListener(topics = "patientDeleteTopic", groupId = "group_id")
    public void deleteConsume(Long id, PatientInput patient, String message) {
        patient.removeIf(t -> t.getId().equals(id));
        log.info("message = {}", message);
    }
}
