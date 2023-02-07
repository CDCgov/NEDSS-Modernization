package gov.cdc.nbs.patientlistener;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;

@SpringBootTest
class PatientListenerApplicationTests {

	@Autowired
	ConcurrentKafkaListenerContainerFactory<String, String> containerFactory;

	@Test
	void contextLoads() {
		assertNotNull(containerFactory);
	}

}
