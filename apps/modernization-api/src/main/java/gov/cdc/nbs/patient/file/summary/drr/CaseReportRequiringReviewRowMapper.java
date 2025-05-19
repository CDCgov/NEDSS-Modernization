package gov.cdc.nbs.patient.file.summary.drr;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

class CaseReportRequiringReviewRowMapper implements RowMapper<DocumentRequiringReview> {

  record Column(
      int identifier,
      int receivedOn,
      int type,
      int sendingFacility,
      int condition,
      int local,
      int updated
  ) {

    Column() {
      this(1, 2, 3, 4, 5, 6, 7);
    }

  }


  private final Column columns;

  CaseReportRequiringReviewRowMapper() {
    this(new Column());
  }

  CaseReportRequiringReviewRowMapper(final Column columns) {
    this.columns = columns;
  }

  @Override
  public DocumentRequiringReview mapRow(final ResultSet resultSet, int rowNum) throws SQLException {
    long identifier = resultSet.getLong(columns.identifier());
    LocalDateTime receivedOn = resultSet.getObject(columns.receivedOn(), LocalDateTime.class);
    String type = resultSet.getString(columns.type());
    String sendingFacility = resultSet.getString(columns.sendingFacility());

    boolean updated = resultSet.getBoolean(columns.updated());
    String local = resultSet.getString(columns.local());

    List<DocumentRequiringReview.Description> descriptions = mapDescription(resultSet);

    return new DocumentRequiringReview(
        identifier,
        local,
        type,
        receivedOn,
        receivedOn,
        false,
        updated,
        null,
        null,
        sendingFacility,
        descriptions
    );
  }

  private List<DocumentRequiringReview.Description> mapDescription(final ResultSet resultSet)
      throws SQLException {
    String condition = resultSet.getString(this.columns.condition());

    return List.of(new DocumentRequiringReview.Description(condition));
  }
}
