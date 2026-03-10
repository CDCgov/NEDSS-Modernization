package gov.cdc.nbs.data.selectable;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class SelectableRowMapper implements RowMapper<Selectable> {

  public record Column(int value, int name) {}

  private final Column columns;

  public SelectableRowMapper(final Column columns) {
    this.columns = columns;
  }

  @Override
  public Selectable mapRow(final ResultSet resultSet, final int rowNum) throws SQLException {
    String value = resultSet.getString(columns.value());
    String name = resultSet.getString(columns.name());

    return (value != null && !value.isBlank()) ? new Selectable(value, name) : null;
  }
}
