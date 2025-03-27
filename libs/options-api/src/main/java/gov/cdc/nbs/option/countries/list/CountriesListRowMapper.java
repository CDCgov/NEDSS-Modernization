package gov.cdc.nbs.option.countries.list;

import org.springframework.jdbc.core.RowMapper;
import gov.cdc.nbs.option.Option;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CountriesListRowMapper implements RowMapper<Option> {
  public record Columns(int value, int name) {
    public Columns() {
      this(1, 2);
    }
  }

  private final Columns columns;

  public CountriesListRowMapper() {
    this(new Columns());
  }

  public CountriesListRowMapper(final Columns columns) {
    this.columns = columns;
  }

  @Override
  public Option mapRow(final ResultSet resultSet, final int row) throws SQLException {
    String value = resultSet.getString(columns.value());
    String name = resultSet.getString(columns.name());

    return new Option(
        value,
        name,
        name,
        0);
  }
}
