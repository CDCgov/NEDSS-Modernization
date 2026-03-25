package gov.cdc.nbs.data.time;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

public class InstantColumnMapper {

  public static Instant map(final ResultSet resultSet, final int column) throws SQLException {

    LocalDateTime localDateTime = resultSet.getObject(column, LocalDateTime.class);

    if (localDateTime == null) {
      return null;
    } else {
      return localDateTime
          .atZone(ZoneId.systemDefault())
          .withZoneSameLocal(ZoneOffset.UTC)
          .toInstant();
    }
  }

  private InstantColumnMapper() {}
}
