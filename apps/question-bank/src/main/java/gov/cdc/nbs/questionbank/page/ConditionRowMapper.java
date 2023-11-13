package gov.cdc.nbs.questionbank.page;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ConditionRowMapper implements RowMapper<SelectableCondition> {

  public record Columns(
      int value,
      int name

  ) {
  }

  private final Columns columns;

  public ConditionRowMapper(final Columns columns) {
    this.columns = columns;
  }

  @Override
  public SelectableCondition mapRow(final ResultSet resultSet, final int rowNum) throws SQLException {
    String value = resultSet.getString(this.columns.value());

    if (value == null) {
      return null;
    }

    String name = resultSet.getString(this.columns.name());
    return new SelectableCondition(value, name);
  }

}
