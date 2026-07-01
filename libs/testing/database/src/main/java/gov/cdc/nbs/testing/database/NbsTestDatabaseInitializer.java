package gov.cdc.nbs.testing.database;

import java.sql.Connection;
import java.sql.Statement;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.images.PullPolicy;

class NbsTestDatabaseInitializer
    implements ApplicationContextInitializer<ConfigurableApplicationContext> {

  private static JdbcDatabaseContainer<?> databaseContainer;

  @Override
  @SuppressWarnings({
    // We don't want to close the container until after tests have completed
    "resource"
  })
  public void initialize(final ConfigurableApplicationContext context) {
    if (databaseContainer == null || !databaseContainer.isRunning()) {
      String image =
          context
              .getEnvironment()
              .getProperty("testing.database.image", "ghcr.io/cdcent/nedssdb:latest");
      String username = context.getEnvironment().getProperty("nbs.datasource.username");
      String credential = context.getEnvironment().getProperty("nbs.datasource.password");

      databaseContainer =
          new NbsDatabaseContainer<>(image)
              .withUsername(username)
              .withPassword(credential)
              .withEnv("DATABASE_VERSION", "6.0.19.1")
              .withImagePullPolicy(PullPolicy.alwaysPull())
              .waitingFor(Wait.forHealthcheck());

      databaseContainer.start();

      // Seed the required configuration entries exactly once
      seedBaselineConfiguration(databaseContainer);
    }

    String url = databaseContainer.getJdbcUrl();

    System.getLogger(NbsTestDatabaseInitializer.class.getCanonicalName())
        .log(System.Logger.Level.INFO, () -> "[url]: %s".formatted(url));

    TestPropertyValues.of("spring.datasource.url=" + url).applyTo(context.getEnvironment());
  }

  /**
   * Directly establishes a connection to the starting container and seeds the required global
   * configuration rows if they are not already present.
   */
  private void seedBaselineConfiguration(final JdbcDatabaseContainer<?> container) {

    // Uses fully qualified database identifiers (NBS_ODSE..NBS_configuration)
    // to insulate against whatever default catalog the connection starts in.
    String seedSql =
        """
        INSERT INTO [NBS_ODSE].[dbo].[NBS_configuration] (
            config_key, config_value, default_value,
            version_ctrl_nbr, add_user_id, add_time,
            last_chg_user_id, last_chg_time, status_cd, status_time
        )
        SELECT
            src.config_key, src.config_value, src.default_value,
            src.version_ctrl_nbr, src.add_user_id, src.add_time,
            src.last_chg_user_id, src.last_chg_time, src.status_cd, src.status_time
        FROM (
            VALUES
            ('REPORT_DB_RDB', 'RDB', 'RDB', 1, 99999999, GETDATE(), 99999999, GETDATE(), 'A', GETDATE()),
            ('REPORT_DB_NBS_ODS', 'NBS_ODSE', 'NBS_ODSE', 1, 99999999, GETDATE(), 99999999, GETDATE(), 'A', GETDATE()),
            ('REPORT_DB_NBS_SRT', 'NBS_SRTE', 'NBS_SRTE', 1, 99999999, GETDATE(), 99999999, GETDATE(), 'A', GETDATE()),
            ('REPORT_DB_NBS_MSG', 'NBS_MSGOUTE', 'NBS_MSGOUTE', 1, 99999999, GETDATE(), 99999999, GETDATE(), 'A', GETDATE()),
            ('REPORT_MAX_ROW_LIMIT_EXPORT', '100000', '100000', 1, 99999999, GETDATE(), 99999999, GETDATE(), 'A', GETDATE()),
            ('REPORT_MAX_ROW_LIMIT_RUN', '10000', '10000', 1, 99999999, GETDATE(), 99999999, GETDATE(), 'A', GETDATE()),
            ('REPORT_EXPORT_DATE_FORMAT', '%m/%d/%Y', '%m/%d/%Y', 1, 99999999, GETDATE(), 99999999, GETDATE(), 'A', GETDATE()),
            ('REPORT_EXPORT_DATETIME_FORMAT', '%m/%d/%Y %H:%M:%S', '%m/%d/%Y %H:%M:%S', 1, 99999999, GETDATE(), 99999999, GETDATE(), 'A', GETDATE())
        ) AS src (
            config_key, config_value, default_value,
            version_ctrl_nbr, add_user_id, add_time,
            last_chg_user_id, last_chg_time, status_cd, status_time
        )
        WHERE NOT EXISTS (
            SELECT 1
            FROM [NBS_ODSE].[dbo].[NBS_configuration] AS config
            WHERE config.config_key = src.config_key
        );
        """;

    try (Connection conn = container.createConnection("");
        Statement stmt = conn.createStatement()) {

      stmt.execute(seedSql);

    } catch (Exception e) {
      throw new IllegalStateException("Failed to seed baseline database configurations", e);
    }
  }
}
