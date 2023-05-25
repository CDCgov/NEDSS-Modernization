package gov.cdc.nbs.authentication;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan({"gov.cdc.nbs.authentication"})
@EnableJpaRepositories({"gov.cdc.nbs.authentication"})
@EntityScan({"gov.cdc.nbs.authentication.entity"})
@Configuration
class AuthenticationConfiguration {
}
