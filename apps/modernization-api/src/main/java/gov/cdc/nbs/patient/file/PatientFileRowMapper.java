package gov.cdc.nbs.patient.file;

import gov.cdc.nbs.data.time.LocalDateColumnMapper;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

class PatientFileRowMapper implements RowMapper<PatientFile> {

  record Columns(int id, int patientId, int local, int status, int sex, int birthday, int deceasedOn) {
  }



  private final Columns columns;


  PatientFileRowMapper(final Columns columns) {

    this.columns = columns;
  }

  @Override
  public PatientFile mapRow(final ResultSet resultSet, final int row) throws SQLException {
    long id = resultSet.getLong(columns.id());
    long patientId = resultSet.getLong(columns.patientId());
    String local = resultSet.getString(columns.local());
    String status = resultSet.getString(columns.status());
    String sex = resultSet.getString(columns.sex());

    LocalDate birthday = LocalDateColumnMapper.map(resultSet, columns.birthday());
    LocalDate deceasedOn = LocalDateColumnMapper.map(resultSet, columns.deceasedOn());

    return new PatientFile(
        id,
        patientId,
        local,
        status,
        null,
        sex,
        birthday,
        deceasedOn,
        null
    );
  }

}
