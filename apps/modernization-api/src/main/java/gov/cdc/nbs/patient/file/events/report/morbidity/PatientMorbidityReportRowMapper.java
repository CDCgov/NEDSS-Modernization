package gov.cdc.nbs.patient.file.events.report.morbidity;

import gov.cdc.nbs.data.time.LocalDateColumnMapper;
import gov.cdc.nbs.demographics.name.DisplayableSimpleName;
import gov.cdc.nbs.demographics.name.DisplayableSimpleNameRowMapper;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;

class PatientMorbidityReportRowMapper implements RowMapper<PatientMorbidityReport> {
  record Column(
      int patient,
      int identifier,
      int local,
      int jurisdiction,
      int addedOn,
      int receivedOn,
      int reportedOn,
      int condition,
      int reportingFacility,
      DisplayableSimpleNameRowMapper.Columns orderingProvider,
      DisplayableSimpleNameRowMapper.Columns reportingProvider
  ) {
    Column() {
      this(1, 2, 3, 4, 5, 6, 7, 8, 9,
          new DisplayableSimpleNameRowMapper.Columns(10, 11, 12),
          new DisplayableSimpleNameRowMapper.Columns(13, 14, 15)
      );
    }
  }


  private final Column columns;
  private final RowMapper<DisplayableSimpleName> orderingProviderMapper;
  private final RowMapper<DisplayableSimpleName> reportingProviderMapper;

  PatientMorbidityReportRowMapper() {
    this(new Column());
  }

  PatientMorbidityReportRowMapper(final Column columns) {
    this.columns = columns;
    this.orderingProviderMapper = new DisplayableSimpleNameRowMapper(columns.orderingProvider);
    this.reportingProviderMapper = new DisplayableSimpleNameRowMapper(columns.reportingProvider);
  }

  @Override
  public PatientMorbidityReport mapRow(final ResultSet resultSet, final int rowNum) throws SQLException {
    long patient = resultSet.getLong(this.columns.patient());
    long identifier = resultSet.getLong(this.columns.identifier());
    String local = resultSet.getString(this.columns.local());
    String jurisdiction = resultSet.getString(this.columns.jurisdiction());
    LocalDateTime addedOn = resultSet.getObject(this.columns.addedOn(), LocalDateTime.class);
    LocalDateTime receivedOn = resultSet.getObject(this.columns.receivedOn(), LocalDateTime.class);
    LocalDate reportedOn = LocalDateColumnMapper.map(resultSet, this.columns.reportedOn());
    String condition = resultSet.getString(this.columns.condition());

    String reportingFacility = resultSet.getString(this.columns.reportingFacility());
    DisplayableSimpleName orderingProvider = this.orderingProviderMapper.mapRow(resultSet, rowNum);
    DisplayableSimpleName reportingProvider = this.reportingProviderMapper.mapRow(resultSet, rowNum);

    return new PatientMorbidityReport(
        patient,
        identifier,
        local,
        jurisdiction,
        addedOn,
        receivedOn,
        reportedOn,
        condition,
        reportingFacility,
        orderingProvider,
        reportingProvider,
        Collections.emptyList(),
        Collections.emptyList(),
        Collections.emptyList()
    );
  }

}
