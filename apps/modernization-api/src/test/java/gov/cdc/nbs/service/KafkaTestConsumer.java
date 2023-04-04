package gov.cdc.nbs.service;

import java.util.concurrent.CountDownLatch;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Component
@Profile("test")
public class KafkaTestConsumer {

    private CountDownLatch latch = new CountDownLatch(1);
    private Object payload;
    private Object key;

    /**
     * Listener to facilitate testing of sent messages
     *
     * @param consumerRecord
     */
    @KafkaListener(topics = {"patient"})
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
