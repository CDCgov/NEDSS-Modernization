package gov.cdc.nbs.config;

import javax.sql.DataSource;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LiquibaseConfig {

  @Value("${spring.liquibase.driver-class-name}")
  private String driverClassName;

  @Value("${spring.datasource.username}")
  private String dbUserName;

  @Value("${spring.datasource.password}")
  private String dbUserPassword;

  @Value("${spring.datasource.url}")
  private String dbUrl;

  @Bean
  @ConfigurationProperties(prefix = "spring.liquibase.report-execution")
  @ConditionalOnProperty(
      prefix = "nbs.ui.features.report.execution",
      name = "enabled",
      havingValue = "true")
  public LiquibaseProperties reportExecutionLiquibaseProperties() {
    return new LiquibaseProperties();
  }

  @Bean
  @ConditionalOnProperty(
      prefix = "nbs.ui.features.report.execution",
      name = "enabled",
      havingValue = "true")
  public DataSource reportExecutionDataSource() {
    return DataSourceBuilder.create()
        .url(dbUrl)
        .username(dbUserName)
        .password(dbUserPassword)
        .driverClassName(driverClassName)
        .build();
  }

  @Bean(name = "reportExecutionLiquibase")
  @ConditionalOnProperty(
      prefix = "nbs.ui.features.report.execution",
      name = "enabled",
      havingValue = "true")
  public SpringLiquibase reportExecutionLiquibase(
      @Qualifier("reportExecutionDataSource") DataSource dataSource,
      @Qualifier("reportExecutionLiquibaseProperties") LiquibaseProperties props) {
    SpringLiquibase liquibase = new SpringLiquibase();
    liquibase.setDataSource(dataSource);
    liquibase.setChangeLog(props.getChangeLog());
    if (liquibase.getContexts() != null) {
      liquibase.setContexts(String.join(",", liquibase.getContexts()));
    }
    return liquibase;
  }
}
