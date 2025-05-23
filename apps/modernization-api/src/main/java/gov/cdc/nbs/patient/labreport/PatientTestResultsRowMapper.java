package gov.cdc.nbs.patient.labreport;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

class PatientTestResultsRowMapper implements RowMapper<PatientLabReport.TestResult> {
  record Column(
      int observationUid,
      int eventId,
      int resultedTest,
      int codedResult,
      int numericResult,
      int highRange,
      int lowRange,
      int unit,
      int status) {
  }

  private final Column columns;

  PatientTestResultsRowMapper(final Column columns) {
    this.columns = columns;
  }

  @Override
  public PatientLabReport.TestResult mapRow(final ResultSet resultSet, final int rowNum) throws SQLException {
    long observationUid = resultSet.getLong(this.columns.observationUid());
    long eventId = resultSet.getLong(this.columns.eventId());
    String resultedTest = resultSet.getString(this.columns.resultedTest());
    String codedResult = resultSet.getString(this.columns.codedResult());
    String numericResult = resultSet.getString(this.columns.numericResult());
    String highRange = resultSet.getString(this.columns.highRange());
    String lowRange = resultSet.getString(this.columns.lowRange());
    String unit = resultSet.getString(this.columns.unit());
    String status = resultSet.getString(this.columns.status());

    return new PatientLabReport.TestResult(
        observationUid,
        eventId,
        resultedTest,
        codedResult,
        numericResult,
        unit,
        highRange,
        lowRange,
        status);
  }
}

