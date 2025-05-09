package gov.cdc.nbs.patient.investigation;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

class PatientInvestigationsRowMapper implements RowMapper<PatientInvestigation> {
  private static final String PAGE_BUILDER_PREFIX = "PG_";

  record Column(
      int investigationid,
      int identifier,
      int startDate,
      int status,
      int condition,
      int caseStatus,
      int notification,
      int jurisdiction,
      int coinfection,
      int investigatorFirstName,
      int investigatorLastName,
      int investigationFormCode) {
  }

  private final Column columns;

  PatientInvestigationsRowMapper(final Column columns) {
    this.columns = columns;
  }

  @Override
  public PatientInvestigation mapRow(final ResultSet resultSet, final int rowNum) throws SQLException {
    long identifier = resultSet.getLong(this.columns.identifier());
    String investigationid = resultSet.getString(this.columns.investigationid());
    String startDate = resultSet.getString(this.columns.startDate());
    startDate = startDate == null ? null : startDate.substring(0, 10);
    String status = resultSet.getString(this.columns.status());
    String caseStatus = resultSet.getString(this.columns.caseStatus());
    String condition = resultSet.getString(this.columns.condition());
    String notification = resultSet.getString(this.columns.notification());
    String jurisdiction = resultSet.getString(this.columns.jurisdiction());
    String coinfection = resultSet.getString(this.columns.coinfection());
    String investigatorFirstName = resultSet.getString(this.columns.investigatorFirstName());
    String investigatorLastName = resultSet.getString(this.columns.investigatorLastName());
    boolean comparable =
        resultSet.getString(this.columns.investigationFormCode()).startsWith(PAGE_BUILDER_PREFIX);

    return new PatientInvestigation(
        investigationid,
        identifier,
        startDate,
        status,
        condition,
        caseStatus,
        notification,
        jurisdiction,
        coinfection,
        new PatientInvestigation.InvestigatorName(investigatorFirstName, investigatorLastName),
        comparable);
  }
}
