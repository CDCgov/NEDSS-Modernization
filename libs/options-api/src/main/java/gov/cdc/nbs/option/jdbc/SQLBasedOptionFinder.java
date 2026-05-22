package gov.cdc.nbs.option.jdbc;

import gov.cdc.nbs.option.Option;
import java.util.Collection;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class SQLBasedOptionFinder {

  private final String query;
  private final JdbcTemplate template;
  private final RowMapper<Option> mapper;

  public SQLBasedOptionFinder(final String query, final JdbcTemplate template) {
    this.query = query;
    this.template = template;
    this.mapper = new OptionRowMapper();
  }

  public Collection<Option> find() {
    return this.template.query(query, this.mapper);
  }
}
