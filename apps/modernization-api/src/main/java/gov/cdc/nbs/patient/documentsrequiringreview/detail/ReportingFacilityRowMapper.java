package gov.cdc.nbs.patient.documentsrequiringreview.detail;

import gov.cdc.nbs.patient.documentsrequiringreview.DocumentRequiringReview;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

class ReportingFacilityRowMapper {

  record Column(int name) {
  }

  private final Column columns;

  ReportingFacilityRowMapper(final Column columns) {
    this.columns = columns;
  }

  Optional<DocumentRequiringReview.ReportingFacility> maybeMap(final ResultSet resultSet) throws SQLException {
    String name = resultSet.getString(columns.name());
    return name == null
        ? Optional.empty()
        : Optional.of(new DocumentRequiringReview.ReportingFacility(name));
  }
}
