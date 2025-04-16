package gov.cdc.nbs.configuration.nbs;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

@Component
@SuppressWarnings("javaarchitecture:S7027")
public class NbsPropertiesFinder {
  public static final String STD_PROGRAM_AREAS = "STD_PROGRAM_AREAS";
  public static final String HIV_PROGRAM_AREAS = "HIV_PROGRAM_AREAS";
  public static final String CODE_BASE = "CODE_BASE";

  private final NamedParameterJdbcTemplate template;

  public NbsPropertiesFinder(
      final NamedParameterJdbcTemplate template) {
    this.template = template;
  }

  private static final String QUERY = """
      SELECT
        config_key,
        config_value
      FROM
        [NBS_ODSE].[dbo].NBS_configuration
      WHERE
        config_key IN (:keys)
      """;

  // List of configs that will be exposed to the UI
  private static final List<String> exposedConfigs = Arrays.asList(HIV_PROGRAM_AREAS, STD_PROGRAM_AREAS, CODE_BASE);


  // Grab the properties from the database and convert them into a map
  public Properties find() {
    SqlParameterSource parameters = new MapSqlParameterSource("keys", exposedConfigs);
    Map<String, String> props = this.template.query(QUERY, parameters, mapper())
        .stream()
        .filter(o -> o.getKey() != null && o.getValue() != null)
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    // convert the map of properties to a Properties object
    return PropertiesMapper.toProperties(props);
  }

  private RowMapper<AbstractMap.SimpleEntry<String, String>> mapper() {
    return new RowMapper<AbstractMap.SimpleEntry<String, String>>() {
      @Override
      public AbstractMap.SimpleEntry<String, String> mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new AbstractMap.SimpleEntry<>(rs.getString(1), rs.getString(2));
      }
    };
  }

}
