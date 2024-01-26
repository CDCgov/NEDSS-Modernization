package gov.cdc.nbs.patient.documentsrequiringreview.detail;

import gov.cdc.nbs.patient.documentsrequiringreview.DocumentRequiringReview;
import gov.cdc.nbs.provider.ProviderNameRowMapper;
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
      int reportingFacility,
      ProviderNameRowMapper.Column ordering,
      int electronic,
      int event
  ) {
  }


  private final Column columns;
  private final ProviderNameRowMapper orderingProviderMapper;

  LaboratoryReportDetailRowMapper(final Column columns) {
    this.columns = columns;
    this.orderingProviderMapper = new ProviderNameRowMapper(columns.ordering);
  }

  @Override
  public DocumentRequiringReview mapRow(final ResultSet resultSet, final int rowNum) throws SQLException {
    long identifier = resultSet.getLong(columns.identifier());
    Instant receivedOn = resultSet.getTimestamp(columns.receivedOn()).toInstant();
    Instant eventDate = maybeMap(resultSet, columns.eventDate());
    boolean electronic = resultSet.getBoolean(columns.electronic());
    String event = resultSet.getString(columns.event());

    DocumentRequiringReview.FacilityProviders providers = mapProviders(resultSet);

    List<DocumentRequiringReview.Description> descriptions = Collections.emptyList();

    return new DocumentRequiringReview(
        identifier,
        event,
        "Laboratory Report",
        eventDate,
        receivedOn,
        electronic,
        false,
        providers,
        descriptions
    );
  }

  private Instant maybeMap(final ResultSet resultSet, final int column) throws SQLException {
    Timestamp timestamp = resultSet.getTimestamp(column);
    return timestamp == null ? null : timestamp.toInstant();
  }

  private DocumentRequiringReview.FacilityProviders mapProviders(final ResultSet resultSet) throws SQLException {

    DocumentRequiringReview.FacilityProviders providers = new DocumentRequiringReview.FacilityProviders();
    String reportingFacility = resultSet.getString(columns.reportingFacility());

    providers.setReportingFacility(new DocumentRequiringReview.ReportingFacility(reportingFacility));

    String orderingProvider = this.orderingProviderMapper.map(resultSet);
    if (orderingProvider != null) {
      providers.setOrderingProvider(new DocumentRequiringReview.OrderingProvider(orderingProvider));
    }

    return providers;
  }


}

