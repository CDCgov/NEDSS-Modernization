package gov.cdc.nbs.demographics.indentification;


import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DisplayableIdentificationRowMapper implements RowMapper<DisplayableIdentification> {


  public record Columns(int type, int value) {
  }


  private final Columns columns;

  public DisplayableIdentificationRowMapper() {
    this(new Columns(1, 2));
  }

  public DisplayableIdentificationRowMapper(final Columns columns) {
    this.columns = columns;
  }

  @Override
  public DisplayableIdentification mapRow(final ResultSet resultSet, final int rowNum) throws SQLException {
    String type = resultSet.getString(columns.type());
    String value = resultSet.getString(columns.value());
    return new DisplayableIdentification(type, value);
  }



}
