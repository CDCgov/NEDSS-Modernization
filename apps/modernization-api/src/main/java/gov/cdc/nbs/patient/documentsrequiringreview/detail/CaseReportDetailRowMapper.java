package gov.cdc.nbs.patient.documentsrequiringreview.detail;

import gov.cdc.nbs.patient.documentsrequiringreview.DocumentRequiringReview;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.List;

class CaseReportDetailRowMapper implements RowMapper<DocumentRequiringReview> {

  record Column(
      int identifier,
      int receivedOn,
      int eventDate,
      int type,
      int electronic,
      int updated,
      int event
  ) {
  }


  private final Column columns;

  CaseReportDetailRowMapper(final Column columns) {
    this.columns = columns;
  }

  @Override
  public DocumentRequiringReview mapRow(final ResultSet resultSet, final int rowNum) throws SQLException {
    long identifier = resultSet.getLong(columns.identifier());
    Instant receivedOn = resultSet.getTimestamp(columns.receivedOn()).toInstant();
    Instant eventDate = resultSet.getTimestamp(columns.eventDate()).toInstant();
    String type = resultSet.getString(columns.type());
    boolean electronic = resultSet.getBoolean(columns.electronic());
    boolean updated = resultSet.getBoolean(columns.updated());
    String event = resultSet.getString(columns.event());

    DocumentRequiringReview.FacilityProviders providers = new DocumentRequiringReview.FacilityProviders();

    return new DocumentRequiringReview(
        identifier,
        event,
        type,
        eventDate,
        receivedOn,
        electronic,
        updated,
        providers,
        List.of()
    );


  }

}
