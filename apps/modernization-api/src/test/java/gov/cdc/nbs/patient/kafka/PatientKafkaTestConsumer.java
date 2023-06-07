package gov.cdc.nbs.patient.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.cdc.nbs.message.patient.event.PatientEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.testcontainers.shaded.org.awaitility.Awaitility;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;

@Component
@Slf4j
public class PatientKafkaTestConsumer {

    public record Message(String key, PatientEvent event) {

    }

    private final ObjectMapper mapper;

    private final List<Message> received;

    public PatientKafkaTestConsumer(final ObjectMapper mapper) {
        this.mapper = mapper;
        this.received = new CopyOnWriteArrayList<>();
    }

    @KafkaListener(topics = "patient", groupId = "PatientKafkaTestConsumer")
    public void receive(final ConsumerRecord<?, ?> consumerRecord) {

        try {
            String key = String.valueOf(consumerRecord.key());
            PatientEvent event = mapper.readValue((String) consumerRecord.value(), PatientEvent.class);

            this.received.add(new Message(key, event));
        } catch (IOException exception) {

            log.error(
                String.format("An error occurred when receiving %s", consumerRecord.value()),
                exception
            );
        }

    }

    public void reset() {
        this.received.clear();
    }

    public List<Message> received() {
        return List.copyOf(this.received);
    }

    public void satisfies(final Consumer<List<Message>> consumer) {
        Awaitility.await()
            .atMost(Duration.ofSeconds(5))
            .pollDelay(Duration.ofSeconds(1))
            .until(this::received, received -> {
                //  run the consumer that should apply assertions
                consumer.accept(received);

                //  if we've gotten here return true.
                return true;
            });

    }

    public void isEmpty() {
        satisfies(actual -> assertThat(actual).isEmpty());
    }
}
