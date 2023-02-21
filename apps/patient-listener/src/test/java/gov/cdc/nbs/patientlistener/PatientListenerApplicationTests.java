package gov.cdc.nbs.patientlistener;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.beans.Transient;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;

import gov.cdc.nbs.entity.odse.Person;

@SpringBootTest
class PatientListenerApplicationTests {

	@Autowired
	ConcurrentKafkaListenerContainerFactory<String, String> containerFactory;

	@Test
	void contextLoads() {
		assertNotNull(containerFactory);
	}


}
