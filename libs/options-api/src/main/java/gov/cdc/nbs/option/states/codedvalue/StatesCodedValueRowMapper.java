package gov.cdc.nbs.option.states.codedvalue;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StatesCodedValueRowMapper implements RowMapper<StateCodedValue> {
  public record Columns(int value, int name, int abbreviation) {
    public Columns() {
      this(1, 2, 3);
    }
  }

  private final Columns columns;

  public StatesCodedValueRowMapper() {
    this(new Columns());
  }

  public StatesCodedValueRowMapper(final Columns columns) {
    this.columns = columns;
  }

  @Override
  public StateCodedValue mapRow(final ResultSet resultSet, final int row) throws SQLException {
    String value = resultSet.getString(columns.value());
    String name = resultSet.getString(columns.name());
    String abbreviation = resultSet.getString(columns.abbreviation());

    return new StateCodedValue(
        value,
        name,
        abbreviation);
  }
}
