package gov.cdc.nbs.patientlistener.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    @KafkaListener(topics = "patientSearchTopic", groupId = "group_id")
    public void consume(String message)
    {
        System.out.println("message = " + message);
    }

    @KafkaListener(topics = "patientDeleteTopic", groupId = "group_id")
    public void deleteConsume(String message)
    {
        System.out.println("message = " + message);
    }
}