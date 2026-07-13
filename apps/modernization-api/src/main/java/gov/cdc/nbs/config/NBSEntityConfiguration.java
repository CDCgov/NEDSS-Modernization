package gov.cdc.nbs.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Enables entity scanning and creation of Spring Data Repositories of the JPA entities defined in
 * the database-entities project.
 */
@Configuration
@EnableJpaRepositories("gov.cdc.nbs.repository")
@EntityScan("gov.cdc.nbs.entity")
class NBSEntityConfiguration {}
