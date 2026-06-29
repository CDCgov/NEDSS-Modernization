package gov.cdc.nbs.testing.database;

import java.lang.System.Logger.Level;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.Container.ExecResult;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.images.PullPolicy;

class NbsTestDatabaseInitializer
    implements ApplicationContextInitializer<ConfigurableApplicationContext> {

  private static final System.Logger LOGGER =
      System.getLogger(NbsTestDatabaseInitializer.class.getCanonicalName());

  @Override
  @SuppressWarnings({"resource"})
  public void initialize(final ConfigurableApplicationContext context) {
    String image =
        context
            .getEnvironment()
            .getProperty("testing.database.image", "ghcr.io/cdcent/nedssdb:latest");
    String username = context.getEnvironment().getProperty("nbs.datasource.username");
    String credential = context.getEnvironment().getProperty("nbs.datasource.password");

    JdbcDatabaseContainer<?> container =
        new NbsDatabaseContainer<>(image)
            .withUsername(username)
            .withPassword(credential)
            .withImagePullPolicy(PullPolicy.alwaysPull());

    container.start();

    runMigrations(container, credential);

    String url = container.getJdbcUrl();

    LOGGER.log(System.Logger.Level.INFO, () -> "[url]: %s".formatted(url));

    TestPropertyValues.of("spring.datasource.url=" + url).applyTo(context.getEnvironment());
  }

  /** Execute the repository migrations within the running container to execute the repository . */
  private void runMigrations(final JdbcDatabaseContainer<?> container, final String credential) {
    try {
      LOGGER.log(Level.INFO, "Database container live. Running repository migrations...");

      var command =
          """
          export PATH="$PATH:/opt/mssql/bin:/opt/mssql-tools18/bin" && \
          export SQLCMDPASSWORD='%s' && \
          export MIGRATIONS_DIR='migrations' && \
          export SEED_DATA_DIR='seed_data' && \
          /var/data/run_migrations.sh %s
          """
              .formatted(credential, "6.0.19.1");

      ExecResult result = container.execInContainer("/bin/bash", "-c", command);

      if (result.getExitCode() != 0) {
        throw new IllegalStateException(
            "Migrations failed (%d): %s".formatted(result.getExitCode(), result.getStdout()));
      }

      LOGGER.log(Level.INFO, "Migrations completed successfully:\n{0}", result.getStdout());

    } catch (Exception e) {
      throw new RuntimeException("Failed to run migrations on the container database", e);
    }
  }
}
