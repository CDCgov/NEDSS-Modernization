package gov.cdc.nbs.patient.documentsrequiringreview;

import org.springframework.jdbc.core.RowCallbackHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

class PatientActivityRequiringReviewResultSetHandler implements RowCallbackHandler {

  record Column(int total, int identifier, int type) {

  }


  private final Column columns;
  private PatientActivityRequiringReview.CaseReport cases;
  private PatientActivityRequiringReview.MorbidityReport morbidities;
  private PatientActivityRequiringReview.LabReport labs;

  PatientActivityRequiringReviewResultSetHandler(final Column columns) {
    this.columns = columns;
    this.cases = new PatientActivityRequiringReview.CaseReport(new ArrayList<>(), 0);
    this.morbidities = new PatientActivityRequiringReview.MorbidityReport(new ArrayList<>(), 0);
    this.labs = new PatientActivityRequiringReview.LabReport(new ArrayList<>(), 0);
  }

  @Override
  public void processRow(final ResultSet resultSet) throws SQLException {
    long identifier = resultSet.getLong(columns.identifier());
    long total = resultSet.getLong(columns.total());
    String type = resultSet.getString(columns.type());

    switch (type) {
      case "LabReport" -> includeLab(identifier, total);
      case "MorbReport" -> includeMorbidity(identifier, total);
      default -> includeCase(identifier, total);
    }

  }

  private void includeCase(final long identifier, final long total) {
    this.cases = new PatientActivityRequiringReview.CaseReport(
        include(this.cases.identifiers(), identifier),
        total
    );
  }

  private Collection<Long> include(final Collection<Long> items, final long item) {
    items.add(item);
    return items;
  }

  private void includeMorbidity(final long identifier, final long total) {
    this.morbidities = new PatientActivityRequiringReview.MorbidityReport(
        include(this.morbidities.identifiers(), identifier),
        total
    );
  }

  private void includeLab(final long identifier, final long total) {
    this.labs = new PatientActivityRequiringReview.LabReport(
        include(this.labs.identifiers(), identifier),
        total
    );
  }

  public PatientActivityRequiringReview activity() {
    return new PatientActivityRequiringReview(
        cases,
        morbidities,
        labs
    );
  }


}
