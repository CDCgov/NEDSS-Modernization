package gov.cdc.nbs.patient.file.demographics.administrative;

import gov.cdc.nbs.data.time.LocalDateColumnMapper;
import gov.cdc.nbs.patient.demographics.administrative.Administrative;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import org.springframework.jdbc.core.RowMapper;

class PatientAdministrativeRowMapper implements RowMapper<Administrative> {

  record Column(int asOf, int comments) {
    Column() {
      this(1, 2);
    }
  }

  private final Column columns;

  PatientAdministrativeRowMapper() {
    this(new Column());
  }

  PatientAdministrativeRowMapper(final Column columns) {
    this.columns = columns;
  }

  @Override
  public Administrative mapRow(final ResultSet resultSet, int rowNum) throws SQLException {
    LocalDate asOf = LocalDateColumnMapper.map(resultSet, columns.asOf());
    String comments = resultSet.getString(columns.comments());
    return new Administrative(asOf, comments);
  }
}
