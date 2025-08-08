package gov.cdc.nbs.patient.file.events.document;

import gov.cdc.nbs.data.time.LocalDateColumnMapper;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

class PatientFileDocumentRowMapper implements RowMapper<PatientFileDocument> {

  record Column(
      int patient,
      int identifier,
      int local,
      int receivedOn,
      int sendingFacility,
      int reportedOn,
      int condition,
      int updated
  ) {

    Column() {
      this(1, 2, 3, 4, 5, 6, 7, 8);
    }

  }


  private final Column columns;

  PatientFileDocumentRowMapper(final Column columns) {
    this.columns = columns;
  }

  PatientFileDocumentRowMapper() {
    this(new Column());
  }

  @Override
  public PatientFileDocument mapRow(final ResultSet resultSet, int rowNum) throws SQLException {
    long patient = resultSet.getLong(columns.patient());
    long identifier = resultSet.getLong(columns.identifier());
    String local = resultSet.getString(columns.local());
    LocalDateTime receivedOn = resultSet.getObject(columns.receivedOn(), LocalDateTime.class);
    LocalDate reportedOn = LocalDateColumnMapper.map(resultSet, columns.reportedOn());
    String sendingFacility = resultSet.getString(columns.sendingFacility());
    String condition = resultSet.getString(this.columns.condition());
    boolean updated = resultSet.getBoolean(columns.updated());

    return new PatientFileDocument(
        patient,
        identifier,
        local,
        receivedOn,
        sendingFacility,
        reportedOn,
        condition,
        updated
    );
  }
}
