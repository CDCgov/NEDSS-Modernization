package gov.cdc.nbs.config;

import javax.sql.DataSource;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "spring.liquibase", name = "enabled", havingValue = "true")
public class LiquibaseConfig {

  private static final String REPORT_EXECUTION_CHANGE_LOG =
      "classpath:db/changelog/report-execution-changelog.yml";

  private static final String CORE_ODSE_CHANGE_LOG = "classpath:db/changelog/odse-changelog.yml";

  @Bean(name = "reportExecutionLiquibase")
  @ConditionalOnProperty(
      prefix = "nbs.ui.features.report.execution",
      name = "enabled",
      havingValue = "true")
  public SpringLiquibase reportExecutionLiquibase(DataSource dataSource) {
    SpringLiquibase liquibase = new SpringLiquibase();
    liquibase.setDataSource(dataSource);
    liquibase.setChangeLog(REPORT_EXECUTION_CHANGE_LOG);
    if (liquibase.getContexts() != null) {
      liquibase.setContexts(String.join(",", liquibase.getContexts()));
    }
    return liquibase;
  }

  @Bean(name = "odseLiquibase")
  public SpringLiquibase odseLiquibase(DataSource dataSource) {
    SpringLiquibase liquibase = new SpringLiquibase();
    liquibase.setDataSource(dataSource);
    liquibase.setChangeLog(CORE_ODSE_CHANGE_LOG);
    if (liquibase.getContexts() != null) {
      liquibase.setContexts(String.join(",", liquibase.getContexts()));
    }
    return liquibase;
  }
}
