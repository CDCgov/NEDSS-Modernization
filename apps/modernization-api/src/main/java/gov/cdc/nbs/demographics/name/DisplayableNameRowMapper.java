package gov.cdc.nbs.demographics.name;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class DisplayableNameRowMapper implements RowMapper<DisplayableName> {

  record Columns(int type, int first, int middle, int last, int suffix) {}

  private final Columns columns;

  public DisplayableNameRowMapper() {
    this(new Columns(1, 2, 3, 4, 5));
  }

  public DisplayableNameRowMapper(final Columns columns) {
    this.columns = columns;
  }

  @Override
  public DisplayableName mapRow(final ResultSet resultSet, final int row) throws SQLException {
    String type = resultSet.getString(columns.type());
    String first = resultSet.getString(columns.first());
    String middle = resultSet.getString(columns.middle());
    String last = resultSet.getString(columns.last());
    String suffix = resultSet.getString(columns.suffix());

    return new DisplayableName(type, first, middle, last, suffix);
  }
}
