package gov.cdc.nbs.patient.search.phone;


import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

class PatientSearchResultPhoneMapper implements RowMapper<PatientSearchResultPhone> {


  record Columns(int type, int use, int number) {
  }


  private final Columns columns;

  PatientSearchResultPhoneMapper() {
    this(new Columns(1, 2, 3));
  }

  PatientSearchResultPhoneMapper(final Columns columns) {
    this.columns = columns;
  }

  @Override
  public PatientSearchResultPhone mapRow(final ResultSet resultSet, final int rowNum) throws SQLException {
    String type = resultSet.getString(columns.type());
    String use = resultSet.getString(columns.use());
    String number = resultSet.getString(columns.number());
    return new PatientSearchResultPhone(type,use, number);
  }

}
