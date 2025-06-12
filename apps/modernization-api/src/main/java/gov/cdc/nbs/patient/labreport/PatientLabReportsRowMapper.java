package gov.cdc.nbs.patient.labreport;

import gov.cdc.nbs.demographics.name.DisplayableSimpleName;
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
      int providerPrefix,
      int providerFirstName,
      int providerLastName,
      int providerSuffix,
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

  PatientLabReportsRowMapper(final Column columns) {
    this.columns = columns;
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
    String providerPrefix = resultSet.getString(this.columns.providerPrefix());
    String providerLastName = resultSet.getString(this.columns.providerLastName());
    String providerFirstName = resultSet.getString(this.columns.providerFirstName());
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
        new DisplayableSimpleName(providerPrefix, providerFirstName, providerLastName), orderingFacility);
  }
}
