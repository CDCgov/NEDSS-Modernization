package gov.cdc.nbs.configuration;

import gov.cdc.nbs.testing.support.EnableSupport;
import javax.sql.DataSource;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
@EnableAutoConfiguration
@ComponentScan
@ConfigurationPropertiesScan
@EnableSupport
public class ConfigurationTestContext {

  @Bean
  JdbcTemplate jdbcTemplate(final DataSource dataSource) {
    return new JdbcTemplate(dataSource);
  }
}
