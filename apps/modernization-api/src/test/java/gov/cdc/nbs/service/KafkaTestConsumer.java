package gov.cdc.nbs.service;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Profile("test")
public class KafkaTestConsumer {

    private final ObjectMapper mapper;

    private CountDownLatch latch = new CountDownLatch(1);
    private Object payload;
    private Object key;

    public KafkaTestConsumer(final ObjectMapper mapper) {
        this.mapper = mapper;
    }

    /**
     * Listener to facilitate testing of sent messages
     *
     */
    @KafkaListener(topics = {"patient"})
    public void receive(ConsumerRecord<?, ?> consumerRecord) {
        payload = consumerRecord.value();
        key = consumerRecord.key();
        log.info("Received message='{}'", payload);
        latch.countDown();
    }

    public void reset () {
        latch = new CountDownLatch(1);
        payload = null;
        key = null;
    }

    public boolean consumed() throws InterruptedException {
        return latch.await(10, TimeUnit.SECONDS);
    }

    public <T> T payload(final Class<T> type) throws JsonProcessingException {
        return mapper.readValue((String)payload, type);
    }

    public Object getPayload() {
        return payload;
    }

    public Object getKey() {
        return key;
    }

    public CountDownLatch getLatch() {
        return latch;
    }

    public void resetLatch() {
        latch = new CountDownLatch(1);
    }
}
