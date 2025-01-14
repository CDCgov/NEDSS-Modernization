package gov.cdc.nbs.testing.database;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.images.PullPolicy;


class NbsTestDatabaseInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

  @Override
  @SuppressWarnings(
      {
          // We don't want to close the container until after tests have completed
          "resource"
      }
  )
  public void initialize(final ConfigurableApplicationContext context) {
    String image = context.getEnvironment().getProperty("testing.database.image", "cdc-sandbox-nbs-mssql");
    String username = context.getEnvironment().getProperty("nbs.datasource.username");
    String credential = context.getEnvironment().getProperty("nbs.datasource.password");

    JdbcDatabaseContainer<?> container = new NbsDatabaseContainer<>(image)
        .withUsername(username)
        .withPassword(credential)
        .withImagePullPolicy(PullPolicy.defaultPolicy());

    container.start();

    String url = container.getJdbcUrl();

    System.getLogger(NbsTestDatabaseInitializer.class.getCanonicalName()).log(
        System.Logger.Level.INFO,
        () -> "[url]: %s".formatted(url)
    );

    TestPropertyValues.of(
            "spring.datasource.url=" + url
        )
        .applyTo(context.getEnvironment());

  }
}
