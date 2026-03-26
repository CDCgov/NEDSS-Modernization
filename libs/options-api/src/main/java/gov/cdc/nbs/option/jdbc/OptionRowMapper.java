package gov.cdc.nbs.option.jdbc;

import gov.cdc.nbs.option.Option;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;

public class OptionRowMapper implements RowMapper<Option> {

  public record Column(int value, int name, int order) {
    Column() {
      this(1, 2, 3);
    }
  }

  private final Column columns;

  public OptionRowMapper() {
    this(new Column());
  }

  public OptionRowMapper(final Column columns) {
    this.columns = columns;
  }

  @Override
  @NonNull public Option mapRow(final ResultSet rs, final int rowNum) throws SQLException {
    String value = rs.getString(columns.value());
    String name = rs.getString(columns.name());
    int order = rs.getInt(columns.order());
    return new Option(value, name, name, order);
  }
}
