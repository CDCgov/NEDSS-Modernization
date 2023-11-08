package gov.cdc.nbs.questionbank.page;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EventTypeRowMapper implements RowMapper<EventType> {

  public record Columns(
      int value,
      int name

  ) {
  }

  private final Columns columns;

  public EventTypeRowMapper(final Columns columns) {
    this.columns = columns;
  }

  @Override
  public EventType mapRow(final ResultSet resultSet, final int rowNum) throws SQLException {
    String value = resultSet.getString(this.columns.value());

    if (value == null) {
      return null;
    }

    String name = resultSet.getString(this.columns.name());
    return new EventType(value, name);
  }

}
