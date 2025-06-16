package gov.cdc.nbs.patient.labreport;

import gov.cdc.nbs.demographics.name.DisplayableSimpleName;
import gov.cdc.nbs.demographics.name.DisplayableSimpleNameRowMapper;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

class PatientLabReportsRowMapper implements RowMapper<PatientLabReport> {
  record Column(
      int investigationId,
      int receiveDate,
      int facilityName,
      int orderingName,
      DisplayableSimpleNameRowMapper.Columns provider,
      int dateCollected,
      int associatedWithId,
      int associatedWithLocal,
      int associatedWithCondition,
      int associatedWithStatus,
      int programArea,
      int jurisdiction,
      int eventId,
      int specimenSite,
      int specimenSource) {
  }


  private final Column columns;
  private final RowMapper<DisplayableSimpleName> providerMapper;

  PatientLabReportsRowMapper(final Column columns) {
    this.columns = columns;
    this.providerMapper = new DisplayableSimpleNameRowMapper(columns.provider);
  }

  @Override
  public PatientLabReport mapRow(final ResultSet resultSet, final int rowNum) throws SQLException {
    String eventId = resultSet.getString(this.columns.eventId());
    LocalDateTime dateReceived = resultSet.getObject(this.columns.receiveDate(), LocalDateTime.class);
    String processingDecision = resultSet.getString(this.columns.specimenSite());
    LocalDateTime dateCollected = resultSet.getObject(this.columns.dateCollected(), LocalDateTime.class);
    String jurisdiction = resultSet.getString(this.columns.jurisdiction());
    String programArea = resultSet.getString(this.columns.programArea());
    long labIdentifier = resultSet.getLong(this.columns.investigationId());
    String reportingFacility = resultSet.getString(this.columns.facilityName());
    DisplayableSimpleName orderingProvider = this.providerMapper.mapRow(resultSet, rowNum);
    String associatedWithId = resultSet.getString(this.columns.associatedWithId());
    String associatedWithLocal = resultSet.getString(this.columns.associatedWithLocal());
    String associatedWithCondition = resultSet.getString(this.columns.associatedWithCondition());
    String associatedWithStatus = resultSet.getString(this.columns.associatedWithStatus());
    String orderingFacility = resultSet.getString(this.columns.orderingName());
    String specimenSource = resultSet.getString(this.columns.specimenSource());



    return new PatientLabReport(
        eventId,
        dateReceived,
        processingDecision,
        dateCollected,
        new ArrayList<>(),
        new PatientLabReport.AssociatedInvestigation(associatedWithId, associatedWithCondition, associatedWithLocal,
            associatedWithStatus),
        programArea, jurisdiction, labIdentifier, specimenSource, reportingFacility,
        orderingProvider, orderingFacility);
  }
}
