package gov.cdc.nbs.demographics.phone;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class DisplayablePhoneRowMapper implements RowMapper<DisplayablePhone> {

  public record Columns(int type, int use, int number) {}

  private final Columns columns;

  public DisplayablePhoneRowMapper() {
    this(new Columns(1, 2, 3));
  }

  public DisplayablePhoneRowMapper(final Columns columns) {
    this.columns = columns;
  }

  @Override
  public DisplayablePhone mapRow(final ResultSet resultSet, final int rowNum) throws SQLException {
    String type = resultSet.getString(columns.type());
    String use = resultSet.getString(columns.use());
    String number = resultSet.getString(columns.number());
    return new DisplayablePhone(type, use, number);
  }
}
