package gov.cdc.nbs.patient.search.phone;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

class PatientSearchResultPhoneMapper implements RowMapper<PatientSearchResultPhone> {

  record Columns(int type, int use, int typeCode, int useCode, int number) {}

  private final Columns columns;

  PatientSearchResultPhoneMapper() {
    this(new Columns(1, 2, 3, 4, 5));
  }

  PatientSearchResultPhoneMapper(final Columns columns) {
    this.columns = columns;
  }

  @Override
  public PatientSearchResultPhone mapRow(final ResultSet resultSet, final int rowNum)
      throws SQLException {
    String type = resultSet.getString(columns.type());
    String use = resultSet.getString(columns.use());
    String typeCode = resultSet.getString(columns.typeCode());
    String useCode = resultSet.getString(columns.useCode());
    String number = resultSet.getString(columns.number());
    String displayUse = resolveDisplayUse(typeCode, useCode, use);
    return new PatientSearchResultPhone(type, displayUse, number);
  }

  private String resolveDisplayUse(final String typeCode, final String useCode, final String use) {
    if (useCode != null && typeCode != null) {
      if ((useCode.equals("WP") || useCode.equals("SB"))) {
        return "Work";
      } else if (useCode.equals("H")) {
        return "Home";
      } else if (useCode.equals("MC")) {
        return "Cell";
      }
    }
    return use;
  }
}
