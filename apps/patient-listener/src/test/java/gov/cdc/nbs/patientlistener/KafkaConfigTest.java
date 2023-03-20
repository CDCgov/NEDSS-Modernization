package gov.cdc.nbs.patientlistener;

import static org.junit.Assert.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJson;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.ActiveProfiles;
import gov.cdc.nbs.patientlistener.config.KafkaConfig;


@AutoConfigureJson
@SpringBootTest(classes = KafkaConfig.class)
@ActiveProfiles("test")
@EmbeddedKafka(partitions = 1, brokerProperties = {"listeners=PLAINTEXT://localhost:9092", "port=9092"})
class KafkaConfigTest {

    @Autowired
    ConcurrentKafkaListenerContainerFactory<String, String> containerFactory;

    @Test
    void should_resolve_beans_for_kafka_resources() {
        assertNotNull(containerFactory);
    }
}
