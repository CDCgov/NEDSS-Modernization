package gov.cdc.nbs.patientlistener;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.ActiveProfiles;

import gov.cdc.nbs.patientlistener.config.KafkaConfig;

@SpringBootTest(classes = {KafkaConfig.class})
@ActiveProfiles("test")
@EmbeddedKafka(partitions = 1, brokerProperties = {"listeners=PLAINTEXT://localhost:9092", "port=9092"})
class PatientListenerApplicationTests {

    @Autowired
    ConcurrentKafkaListenerContainerFactory<String, String> containerFactory;

    @Test
    void contextLoads() {
        assertNotNull(containerFactory);
    }
}
