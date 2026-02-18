package gov.cdc.nbs.id;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan({"gov.cdc.nbs.id"})
@EnableJpaRepositories({"gov.cdc.nbs.id"})
@EntityScan({"gov.cdc.nbs.id"})
@Configuration
public class IdGeneratorConfig {}
