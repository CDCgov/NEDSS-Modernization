package gov.cdc.nbs.service;

import java.util.concurrent.CountDownLatch;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@Getter
public class KafkaTestConsumer {

    private CountDownLatch latch = new CountDownLatch(1);
    private Object payload;
    private Object key;

    /**
     * Listens to all topics
     * 
     * @param consumerRecord
     */
    @KafkaListener(topics = "#{'${kafka.listener.topics}'.split(',')}")
    public void receive(ConsumerRecord<?, ?> consumerRecord) {
        payload = consumerRecord.value();
        key = consumerRecord.key();
        log.info("Received message='{}'", payload);
        latch.countDown();
    }

    public void resetLatch() {
        latch = new CountDownLatch(1);
    }

    // other getters
}