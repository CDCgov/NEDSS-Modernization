package gov.cdc.nbs.patientlistener;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Enables entity scanning and creation of Spring Data Repositories of the Patient related JPA entities defined in the
 * database-entities project.
 */
@ComponentScan({"gov.cdc.nbs.patient"})
@EnableJpaRepositories({"gov.cdc.nbs.repository"})
@EntityScan("gov.cdc.nbs.entity")
@Configuration
class PatientDataConfiguration {
}
