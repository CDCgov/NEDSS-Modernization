package gov.cdc.nbs.option.counties.list;

import gov.cdc.nbs.option.Option;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class CountiesListRowMapper implements RowMapper<Option> {
  public record Columns(int value, int name) {
    public Columns() {
      this(1, 2);
    }
  }

  private final Columns columns;

  public CountiesListRowMapper() {
    this(new Columns());
  }

  public CountiesListRowMapper(final Columns columns) {
    this.columns = columns;
  }

  @Override
  public Option mapRow(final ResultSet resultSet, final int row) throws SQLException {
    String value = resultSet.getString(columns.value());
    String name = resultSet.getString(columns.name());

    return new Option(value, name, name, 0);
  }
}
