package gov.cdc.nbs.sql;

import java.sql.ResultSet;
import java.sql.SQLException;

public class IntegerColumnMapper {

  public static Integer map(final ResultSet resultSet, final int column) throws SQLException {
    int value = resultSet.getInt(column);
    return resultSet.wasNull() ? null : value;
  }

  private IntegerColumnMapper() {
    // static
  }
}
