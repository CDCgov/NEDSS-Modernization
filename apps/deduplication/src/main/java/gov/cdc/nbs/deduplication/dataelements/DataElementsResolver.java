package gov.cdc.nbs.deduplication.dataelements;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import gov.cdc.nbs.deduplication.dataelements.model.DataElementConfiguration;
import gov.cdc.nbs.deduplication.dataelements.model.DataElementConfigurationResponse;
import gov.cdc.nbs.deduplication.exception.ConfigurationParsingException;

@Component
public class DataElementsResolver {

  private final JdbcTemplate template;
  private final ObjectMapper mapper;

  private static final String QUERY = """
      SELECT
        TOP 1 configuration
      FROM
        data_element_configuration
        ORDER BY add_time desc;
                 """;

  public DataElementsResolver(final JdbcTemplate template, final ObjectMapper mapper) {
    this.template = template;
    this.mapper = mapper;
  }

  public DataElementConfigurationResponse resolveCurrent() {
    final List<String> configurations = template.queryForList(QUERY, String.class);
    if (configurations.isEmpty()) {
      return new DataElementConfigurationResponse(null);
    }
    try {
      return new DataElementConfigurationResponse(
          mapper.readValue(configurations.get(0), DataElementConfiguration.class));
    } catch (JsonProcessingException e) {
      throw new ConfigurationParsingException();
    }

  }
}
