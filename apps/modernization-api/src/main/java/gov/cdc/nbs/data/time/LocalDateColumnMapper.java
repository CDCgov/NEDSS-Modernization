package gov.cdc.nbs.data.time;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class LocalDateColumnMapper {

  public static LocalDate map(final ResultSet resultSet, final int column) throws SQLException {

    LocalDateTime localDateTime = resultSet.getObject(column, LocalDateTime.class);

    if (localDateTime == null) {
      return null;
    } else {
      return localDateTime.toLocalDate();
    }
  }

  private LocalDateColumnMapper() {}
}
