package gov.cdc.nbs.datasource.utils;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

@Component
public class ConfigurationValueFinder {

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
      return "";
    }
  }
}
