package gov.cdc.nbs.option.jdbc;

import gov.cdc.nbs.option.Option;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;

public class OptionRowMapper implements RowMapper<Option> {

  public OptionRowMapper() {}

  @Override
  @NonNull public Option mapRow(final ResultSet rs, final int rowNum) throws SQLException {
    // "value" and "name" must be specified as columns in the return
    String value = rs.getString("value");
    String name = rs.getString("name");

    // "order" and "label" are optional - populate if available or use a reasonable default
    ResultSetMetaData meta = rs.getMetaData();
    int cnt = meta.getColumnCount();
    int order = 1;
    String label = name;
    for (int i = 1; i <= cnt; i++) {
      String colName = meta.getColumnName(i);
      if ("order".equals(colName)) {
        order = rs.getInt(i);
      } else if ("label".equals(colName)) {
        label = rs.getString(i);
      }
    }
    return new Option(value, name, label, order);
  }
}
