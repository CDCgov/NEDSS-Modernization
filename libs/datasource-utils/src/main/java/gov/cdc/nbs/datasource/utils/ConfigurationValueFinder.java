package gov.cdc.nbs.datasource.utils;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.dao.TypeMismatchDataAccessException;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

@Component
public class ConfigurationValueFinder {

  private static final System.Logger LOGGER =
          System.getLogger(ConfigurationValueFinder.class.getName());

  private final JdbcClient client;

  private static final String CONFIG_QUERY =
          """
          SELECT COALESCE(config_value, default_value)
          FROM NBS_ODSE..NBS_configuration
          WHERE config_key = ?
          """;

  public ConfigurationValueFinder(final JdbcClient client) {
    this.client = client;
  }

  public String getConfigValue(String key) {
    try {
      return this.client.sql(CONFIG_QUERY).param(key).query(String.class).single();
    } catch (EmptyResultDataAccessException e) {
      LOGGER.log(
              System.Logger.Level.ERROR,
              "Database anomaly: Zero configuration rows found for key: {0}", key);
      return "";
    } catch (IncorrectResultSizeDataAccessException e) {
      // Critical anomaly: Multiple records exist for a single primary/unique config key
      LOGGER.log(
              System.Logger.Level.ERROR,
              "Database anomaly: Multiple configuration rows found for key: {0}", key);
      return "";
    } catch (TypeMismatchDataAccessException e) {
      // Anomaly: The row exists, but both config_value and default_value columns resolved to null
      LOGGER.log(
              System.Logger.Level.WARNING,
              "Configuration key ({0}) exists, but both config_value and default_value are null", key);
      return "";
    }
  }
}
