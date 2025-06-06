package gov.cdc.nbs.patient.file.events.investigation;

import gov.cdc.nbs.data.time.LocalDateColumnMapper;
import gov.cdc.nbs.demographics.name.DisplayableSimpleName;
import gov.cdc.nbs.demographics.name.DisplayableSimpleNameRowMapper;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

class PatientInvestigationsRowMapper implements RowMapper<PatientInvestigation> {
  private static final String PAGE_BUILDER_PREFIX = "PG_";


  record Column(
      int patient,
      int identifier,
      int local,
      int startDate,
      int status,
      int condition,
      int caseStatus,
      int notification,
      int jurisdiction,
      int coinfection,
      DisplayableSimpleNameRowMapper.Columns investigator,
      int investigationFormCode
  ) {
  }


  private final Column columns;
  private final RowMapper<DisplayableSimpleName> investigatorMapper;

  PatientInvestigationsRowMapper(final Column columns) {
    this.columns = columns;
    this.investigatorMapper = new DisplayableSimpleNameRowMapper(columns.investigator());
  }

  @Override
  public PatientInvestigation mapRow(final ResultSet resultSet, final int rowNum) throws SQLException {
    long patient = resultSet.getLong(this.columns.patient());
    long identifier = resultSet.getLong(this.columns.identifier());
    String local = resultSet.getString(this.columns.local());
    LocalDate startedOn = LocalDateColumnMapper.map(resultSet, columns.startDate());
    String status = resultSet.getString(this.columns.status());
    String caseStatus = resultSet.getString(this.columns.caseStatus());
    String condition = resultSet.getString(this.columns.condition());
    String notification = resultSet.getString(this.columns.notification());
    String jurisdiction = resultSet.getString(this.columns.jurisdiction());
    String coinfection = resultSet.getString(this.columns.coinfection());

    DisplayableSimpleName investigator = this.investigatorMapper.mapRow(resultSet, rowNum);

    boolean comparable =
        resultSet.getString(this.columns.investigationFormCode()).startsWith(PAGE_BUILDER_PREFIX);

    return new PatientInvestigation(
        patient,
        identifier,
        local,
        startedOn,
        status,
        condition,
        caseStatus,
        notification,
        jurisdiction,
        coinfection,
        investigator,
        comparable
    );
  }
}
