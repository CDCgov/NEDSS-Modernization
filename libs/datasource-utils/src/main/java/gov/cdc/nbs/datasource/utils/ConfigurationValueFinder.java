package gov.cdc.nbs.datasource.utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
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

  private final Map<String, String> cache = new ConcurrentHashMap<>();

  private static final String CONFIG_QUERY =
      """
              SELECT config_value
              FROM NBS_ODSE..NBS_configuration
              WHERE config_key = ?
              """;

  public ConfigurationValueFinder(final JdbcClient client) {
    this.client = client;
  }

  public String getConfigValue(String key) {
    if (key == null || key.isBlank()) {
      return "";
    }

    // Normalize casing first
    String normalizedKey = key.toUpperCase();

    // Look up from memory
    if (this.cache.containsKey(normalizedKey)) {
      return this.cache.get(normalizedKey);
    }

    try {
      // Query database using the normalized key name
      String value =
          this.client.sql(CONFIG_QUERY).param(normalizedKey).query(String.class).single();

      if (value != null) {
        String resolvedValue = value.strip();
        this.cache.put(normalizedKey, resolvedValue);
        return resolvedValue;
      }

      return "";

    } catch (EmptyResultDataAccessException e) {
      LOGGER.log(
          System.Logger.Level.ERROR,
          "Zero rows found in NBS_Configuration for unique key: {0}",
          key);
      return "";
    } catch (IncorrectResultSizeDataAccessException e) {
      LOGGER.log(
          System.Logger.Level.ERROR,
          "Multiple rows found in NBS_Configuration for unique key: {0}",
          key);
      return "";
    } catch (TypeMismatchDataAccessException e) {
      LOGGER.log(
          System.Logger.Level.WARNING,
          "Configuration key ({0}) exists in NBS_Configuration, but both config_value and default_value are null",
          key);
      return "";
    }
  }

  /**
   * Batch-loads and caches all keys required by the application runtime. Throws an exception if any
   * required key cannot be loaded.
   */
  public void loadConfigurations() {
    LOGGER.log(System.Logger.Level.INFO, "Loading baseline system configuration cache...");

    String[] requiredKeys = {
      "REPORT_DB_NBS_RDB",
      "REPORT_DB_NBS_ODS",
      "REPORT_DB_NBS_SRT",
      "REPORT_DB_NBS_MSG",
      "REPORT_MAX_ROW_LIMIT_RUN",
      "REPORT_MAX_ROW_LIMIT_EXPORT",
      "REPORT_EXPORT_DATE_FORMAT",
      "REPORT_EXPORT_DATETIME_FORMAT"
    };

    for (String key : requiredKeys) {
      String value = getConfigValue(key);

      // Strict Check: If a required key comes back blank, prevent the application from working in a
      // broken state
      if (value == null || value.isBlank()) {
        throw new IllegalStateException(
            "CRITICAL STARTUP FAILURE: Required configuration key '"
                + key
                + "' is missing or invalid in NBS_Configuration database table.");
      }
    }

    LOGGER.log(
        System.Logger.Level.INFO, "Baseline system configuration cache loaded successfully.");
  }

  /**
   * Clears the in-memory lookup cache. Call this inside your test hooks (e.g.,
   * Cucumber @Before/@After annotations) to guarantee complete state isolation between your test
   * boundaries.
   */
  public void clearCache() {
    this.cache.clear();
    LOGGER.log(System.Logger.Level.INFO, "Configuration cache cleared.");
  }
}
