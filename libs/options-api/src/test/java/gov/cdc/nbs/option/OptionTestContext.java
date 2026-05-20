package gov.cdc.nbs.option;

import gov.cdc.nbs.configuration.nbs.NbsPropertiesFinder;
import gov.cdc.nbs.configuration.nbs.Properties;
import java.util.List;
import javax.sql.DataSource;
import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class OptionTestContext {
  @Bean
  JdbcTemplate jdbcTemplate(final DataSource dataSource) {
    return new JdbcTemplate(dataSource);
  }

  @Bean
  public NbsPropertiesFinder nbsPropertiesFinder() {
    NbsPropertiesFinder finder = Mockito.mock(NbsPropertiesFinder.class);
    Properties mockProperties = Mockito.mock(Properties.class);

    Mockito.when(mockProperties.hivProgramAreas()).thenReturn(List.of("HIV"));
    Mockito.when(mockProperties.stdProgramAreas()).thenReturn(List.of("STD"));
    Mockito.when(finder.find()).thenReturn(mockProperties);

    return finder;
  }
}
