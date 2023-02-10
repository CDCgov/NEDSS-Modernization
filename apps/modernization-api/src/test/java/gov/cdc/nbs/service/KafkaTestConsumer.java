package gov.cdc.nbs.service;

import java.util.concurrent.CountDownLatch;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.kafka.annotation.KafkaListener;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@TestComponent
public class KafkaTestConsumer {

    private CountDownLatch latch = new CountDownLatch(1);
    private Object payload;
    private Object key;

    /**
     * Listener to facilitate testing of sent messages
     * 
     * @param consumerRecord
     */
    @KafkaListener(topics = { "request-patient-search.patientsearch",
            "request-patient-search.patientupdate",
            "request-patient-search.patientdelete",
            "request-patient-search.patientcreate"
    })
    public void receive(ConsumerRecord<?, ?> consumerRecord) {
        payload = consumerRecord.value();
        key = consumerRecord.key();
        log.info("Received message='{}'", payload);
        latch.countDown();
    }

    public void resetLatch() {
        latch = new CountDownLatch(1);
    }

}