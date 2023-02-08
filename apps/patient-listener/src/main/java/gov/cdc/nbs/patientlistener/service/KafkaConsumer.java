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
}