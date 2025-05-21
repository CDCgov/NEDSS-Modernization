package gov.cdc.nbs.patient.labreport;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

class PatientTestResultsRowMapper implements RowMapper<PatientLabReport.TestResult> {
  record Column(
      int identifier,
      int investigationid,
      int startDate,
      int status,
      int condition,
      int caseStatus) {
  }

  private final Column columns;

  PatientTestResultsRowMapper(final Column columns) {
    this.columns = columns;
  }

  @Override
  public PatientLabReport.TestResult mapRow(final ResultSet resultSet, final int rowNum) throws SQLException {
    long identifier = resultSet.getLong(this.columns.identifier());
    String investigationid = resultSet.getString(this.columns.investigationid());
    String startDate = resultSet.getString(this.columns.startDate());
    startDate = startDate == null ? null : startDate.substring(0, 10);
    String status = resultSet.getString(this.columns.status());
    String caseStatus = resultSet.getString(this.columns.caseStatus());
    String condition = resultSet.getString(this.columns.condition());

    return new PatientLabReport.TestResult(
        identifier,
        investigationid,
        startDate,
        startDate,
        status,
        condition,
        caseStatus);
  }
}
