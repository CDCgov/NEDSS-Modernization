package gov.cdc.nbs.patientlistener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan({ "gov.cdc.nbs" })
@EnableJpaRepositories("gov.cdc.nbs")
@EntityScan("gov.cdc.nbs.entity")
public class PatientListenerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PatientListenerApplication.class, args);
	}

}
