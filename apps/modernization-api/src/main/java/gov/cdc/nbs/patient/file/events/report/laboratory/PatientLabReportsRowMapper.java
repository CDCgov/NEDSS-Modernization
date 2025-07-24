package gov.cdc.nbs.patient.file.events.report.laboratory;

import gov.cdc.nbs.data.time.LocalDateColumnMapper;
import gov.cdc.nbs.demographics.name.DisplayableSimpleName;
import gov.cdc.nbs.demographics.name.DisplayableSimpleNameRowMapper;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;

class PatientLabReportsRowMapper implements RowMapper<PatientLabReport> {
  record Column(
      int patient,
      int identifier,
      int local,
      int programArea,
      int jurisdiction,
      int processingDecision,
      int dateReceived,
      int electronic,
      int reportingFacility,
      int orderingFacility,
      DisplayableSimpleNameRowMapper.Columns orderingProvider,
      int dateCollected,
      int specimenSite,
      int specimenSource
  ) {
    Column() {
      this(1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
          new DisplayableSimpleNameRowMapper.Columns(11, 12, 13),
          14, 15, 16
      );
    }
  }


  private final Column columns;
  private final RowMapper<DisplayableSimpleName> providerMapper;

  PatientLabReportsRowMapper() {
    this(new Column());
  }

  PatientLabReportsRowMapper(final Column columns) {
    this.columns = columns;
    this.providerMapper = new DisplayableSimpleNameRowMapper(columns.orderingProvider);
  }

  @Override
  public PatientLabReport mapRow(final ResultSet resultSet, final int rowNum) throws SQLException {
    long patient = resultSet.getLong(this.columns.patient());
    long labIdentifier = resultSet.getLong(this.columns.identifier());
    String local = resultSet.getString(this.columns.local());
    LocalDateTime dateReceived = resultSet.getObject(this.columns.dateReceived(), LocalDateTime.class);
    boolean electronic = resultSet.getBoolean(this.columns.electronic());
    String processingDecision = resultSet.getString(this.columns.processingDecision());
    LocalDate dateCollected = LocalDateColumnMapper.map(resultSet, this.columns.dateCollected());
    String jurisdiction = resultSet.getString(this.columns.jurisdiction());
    String programArea = resultSet.getString(this.columns.programArea());
    String reportingFacility = resultSet.getString(this.columns.reportingFacility());
    DisplayableSimpleName orderingProvider = this.providerMapper.mapRow(resultSet, rowNum);
    String orderingFacility = resultSet.getString(this.columns.orderingFacility());

    PatientLabReport.Specimen specimen = mapSpecimen(resultSet);

    return new PatientLabReport(
        patient,
        labIdentifier,
        local,
        programArea,
        jurisdiction,
        dateReceived,
        electronic,
        processingDecision,
        dateCollected,
        Collections.emptyList(),
        reportingFacility,
        orderingProvider,
        orderingFacility,
        specimen,
        Collections.emptyList()
    );
  }

  private PatientLabReport.Specimen mapSpecimen(final ResultSet resultSet) throws SQLException {
    String source = resultSet.getString(this.columns.specimenSource());
    String site = resultSet.getString(this.columns.specimenSite());

    return (source != null || site != null) ? new PatientLabReport.Specimen(site, source) : null;
  }
}
