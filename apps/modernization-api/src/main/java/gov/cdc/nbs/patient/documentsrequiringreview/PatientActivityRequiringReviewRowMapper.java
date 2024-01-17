package gov.cdc.nbs.patient.documentsrequiringreview;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

class PatientActivityRequiringReviewRowMapper implements RowMapper<PatientActivityRequiringReview> {

  record Column(int total, int identifier, int type) {
  }


  private final Column columns;

  PatientActivityRequiringReviewRowMapper(final Column columns) {
    this.columns = columns;
  }

  @Override
  public PatientActivityRequiringReview mapRow(final ResultSet resultSet, final int rowNum) throws SQLException {
    long identifier = resultSet.getLong(columns.identifier());
    long total = resultSet.getLong(columns.total());
    String type = resultSet.getString(columns.type());

    return switch (type) {
      case "LabReport" -> new PatientActivityRequiringReview.LabReport(identifier, total);

      case "MorbReport" -> new PatientActivityRequiringReview.MorbidityReport(identifier, total);

      default -> new PatientActivityRequiringReview.CaseReport(identifier, type, total);

    };


  }

}
