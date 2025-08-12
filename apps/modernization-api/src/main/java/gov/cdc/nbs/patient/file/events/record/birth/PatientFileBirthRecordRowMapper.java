package gov.cdc.nbs.patient.file.events.record.birth;

import gov.cdc.nbs.data.time.LocalDateColumnMapper;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

class PatientFileBirthRecordRowMapper implements RowMapper<PatientFileBirthRecord> {

  record Column(
      int patient,
      int identifier,
      int local,
      int receivedOn,
      int facility,
      int collectedOn,
      int certificate
  ) {
    Column() {
      this(1, 2, 3, 4, 5, 6, 7);
    }
  }


  private final Column columns;

  PatientFileBirthRecordRowMapper() {
    this(new Column());
  }

  PatientFileBirthRecordRowMapper(final Column columns) {
    this.columns = columns;
  }

  @Override
  public PatientFileBirthRecord mapRow(final ResultSet resultSet, final int rowNum) throws SQLException {

    long patient = resultSet.getLong(this.columns.patient);
    long identifier = resultSet.getLong(this.columns.identifier);
    String local = resultSet.getString(this.columns.local);
    LocalDateTime receivedOn = resultSet.getObject(this.columns.receivedOn, LocalDateTime.class);
    String facility = resultSet.getString(this.columns.facility);
    LocalDate collectedOn = LocalDateColumnMapper.map(resultSet, this.columns.collectedOn);
    String certificate = resultSet.getString(this.columns.certificate);

    return new PatientFileBirthRecord(
        patient,
        identifier,
        local,
        receivedOn,
        facility,
        collectedOn,
        certificate
    );
  }
}
