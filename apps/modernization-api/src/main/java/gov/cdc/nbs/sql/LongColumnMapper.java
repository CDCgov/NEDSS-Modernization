package gov.cdc.nbs.sql;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LongColumnMapper {

  public static Long map(final ResultSet resultSet, final int column) throws SQLException {
    long value = resultSet.getLong(column);
    return resultSet.wasNull() ? null : value;
  }

  private LongColumnMapper() {
    // static
  }
}
