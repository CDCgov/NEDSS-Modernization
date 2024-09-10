package gov.cdc.nbs.patient.documentsrequiringreview.detail;

import gov.cdc.nbs.patient.documentsrequiringreview.DocumentRequiringReview;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collections;
import java.util.List;

class LaboratoryReportDetailRowMapper implements RowMapper<DocumentRequiringReview> {

  record Column(
      int identifier,
      int receivedOn,
      int eventDate,
      FacilityProvidersRowMapper.Column facilities,
      int electronic,
      int event,
      LabTestSummaryRowMapper.Column tests) {
  }


  private final Column columns;
  private final FacilityProvidersRowMapper facilityProvidersRowMapper;
  private final LabTestSummaryRowMapper labTestSummaryMapper;

  LaboratoryReportDetailRowMapper(final Column columns) {
    this.columns = columns;
    this.facilityProvidersRowMapper = new FacilityProvidersRowMapper(columns.facilities());
    this.labTestSummaryMapper = new LabTestSummaryRowMapper(columns.tests());
  }

  @Override
  public DocumentRequiringReview mapRow(final ResultSet resultSet, final int rowNum) throws SQLException {
    long identifier = resultSet.getLong(columns.identifier());
    Instant receivedOn = resultSet.getTimestamp(columns.receivedOn()).toInstant();
    Instant eventDate = maybeMap(resultSet, columns.eventDate());
    boolean electronic = resultSet.getBoolean(columns.electronic());
    String event = resultSet.getString(columns.event());

    DocumentRequiringReview.FacilityProviders providers = mapProviders(resultSet);

    List<DocumentRequiringReview.Description> descriptions = maybeMapTests(resultSet);

    return new DocumentRequiringReview(
        identifier,
        event,
        "Laboratory Report",
        eventDate,
        receivedOn,
        electronic,
        false,
        providers,
        descriptions);
  }

  private Instant maybeMap(final ResultSet resultSet, final int column) throws SQLException {
    Timestamp timestamp = resultSet.getTimestamp(column);
    return timestamp == null ? null : timestamp.toInstant();
  }

  private DocumentRequiringReview.FacilityProviders mapProviders(final ResultSet resultSet) throws SQLException {
    return this.facilityProvidersRowMapper.map(resultSet);
  }

  private List<DocumentRequiringReview.Description> maybeMapTests(final ResultSet resultSet) throws SQLException {
    LabTestSummary summary = this.labTestSummaryMapper.mapRow(resultSet, 0);

    return LabTestSummaryDescriptionMapper.maybeMap(summary).map(List::of).orElse(Collections.emptyList());
  }

}

