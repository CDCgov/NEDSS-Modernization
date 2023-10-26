package gov.cdc.nbs.testing.database;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.images.PullPolicy;


class NbsTestDatabaseInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

  private static final String DEFAULT_IMAGE_VALUE = "cdc-sandbox-nbs-mssql";

  @Override
  @SuppressWarnings(
      {
          // We don't want to close the container until after tests have completed
          "resource",
          // The database container and credentials are for development only, working on
          // removing the credentials entirely.
          "secrets:S6703",
          "java:S2068"
      }
  )
  public void initialize(final ConfigurableApplicationContext context) {
    String image = context.getEnvironment().getProperty("testing.database.image", DEFAULT_IMAGE_VALUE);
    NbsDatabaseContainer container = new NbsDatabaseContainer(image)
        .withImagePullPolicy(PullPolicy.defaultPolicy());

    container.start();

    TestPropertyValues.of(
            "spring.datasource.url=" + container.url(),
            "spring.datasource.username=sa",
            "spring.datasource.password=fake.fake.fake.1234"
        )
        .applyTo(context.getEnvironment());

  }
}
