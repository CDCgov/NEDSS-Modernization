package gov.cdc.nbs.patient.labreport;

import org.springframework.jdbc.core.RowMapper;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

class PatientLabReportsRowMapper implements RowMapper<PatientLabReport> {
  record Column(
      int investigationId,
      int receiveDate,
      int facilityName,
      int providerPrefix,
      int providerFirstName,
      int providerLastName,
      int providerSuffix,
      int dateCollected,
      int associatedWithId,
      int associatedWithLocal,
      int associatedWithCondition,
      int programArea,
      int jurisdiction,
      int eventId,
      int specimenSite,
      int specimenSource,
      int labTest,
      int labResultStatus,
      int codedResult,
      int numericResult,
      int highRange,
      int lowRange) {
  }

  private final Column columns;

  PatientLabReportsRowMapper(final Column columns) {
    this.columns = columns;
  }

  @Override
  public PatientLabReport mapRow(final ResultSet resultSet, final int rowNum) throws SQLException {
    String eventId = resultSet.getString(this.columns.eventId());
    String dateReceived = resultSet.getString(this.columns.receiveDate());
    dateReceived = dateReceived == null ? null : dateReceived.substring(0, 10);
    String processingDecision = resultSet.getString(this.columns.specimenSite());
    String dateCollected = resultSet.getString(this.columns.dateCollected());
    String jurisdiction = resultSet.getString(this.columns.jurisdiction());
    String programArea = resultSet.getString(this.columns.programArea());
    String labIdentifier = resultSet.getString(this.columns.investigationId());
    String reportingFacility = resultSet.getString(this.columns.facilityName());
    String providerLastName = resultSet.getString(this.columns.providerLastName());
    String labTest = resultSet.getString(this.columns.labTest());
    String labResultStatus = resultSet.getString(this.columns.labResultStatus());
    String codedResult = resultSet.getString(this.columns.codedResult());
    String numericResult = resultSet.getString(this.columns.numericResult());
    String highRange = resultSet.getString(this.columns.highRange());
    String lowRange = resultSet.getString(this.columns.lowRange());
    String associatedWithId = resultSet.getString(this.columns.associatedWithId());
    String associatedWithLocal = resultSet.getString(this.columns.associatedWithLocal());
    String associatedWithCondition = resultSet.getString(this.columns.associatedWithCondition());

    return new PatientLabReport(
        eventId,
        dateReceived,
        processingDecision,
        new PatientLabReport.FacilityProviders(reportingFacility, providerLastName, null),
        dateCollected,
        new PatientLabReport.TestResult(1, labTest, codedResult, numericResult, lowRange, highRange,
            labResultStatus),
        new PatientLabReport.AssociatedInvestigation(associatedWithId, associatedWithCondition, associatedWithLocal),
        programArea, jurisdiction, labIdentifier);
  }
}
