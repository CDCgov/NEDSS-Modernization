package gov.cdc.nbs.deduplication;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DataManager {
  private static final String RESET_DATA = """
      UPDATE person
      SET dedup_match_ind = null,
      group_nbr = null,
      group_time = null
      """;

  private final JdbcTemplate template;

  public DataManager(final JdbcTemplate template) {
    this.template = template;
  }

  public int reset() {
    return template.update(RESET_DATA);
  }
}
