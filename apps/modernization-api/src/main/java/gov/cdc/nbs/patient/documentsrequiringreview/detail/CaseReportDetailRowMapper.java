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
      int type,
      int sendingFacility,
      int condition,
      int event,
      int updated
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
    String type = resultSet.getString(columns.type());
    boolean updated = resultSet.getBoolean(columns.updated());
    String event = resultSet.getString(columns.event());

    DocumentRequiringReview.FacilityProviders providers = mapProviders(resultSet);

    List<DocumentRequiringReview.Description> descriptions = mapDescription(resultSet);

    return new DocumentRequiringReview(
        identifier,
        event,
        type,
        receivedOn,
        receivedOn,
        false,
        updated,
        providers,
        descriptions
    );


  }

  private DocumentRequiringReview.FacilityProviders mapProviders(final ResultSet resultSet) throws SQLException {
    String sendingFacility = resultSet.getString(columns.sendingFacility());

    DocumentRequiringReview.FacilityProviders providers = new DocumentRequiringReview.FacilityProviders();
    providers.setSendingFacility(new DocumentRequiringReview.SendingFacility(sendingFacility));

    return providers;
  }

  private List<DocumentRequiringReview.Description> mapDescription(final ResultSet resultSet)
      throws SQLException {
    String condition = resultSet.getString(this.columns.condition());

    return List.of(new DocumentRequiringReview.Description(condition, ""));
  }
}
