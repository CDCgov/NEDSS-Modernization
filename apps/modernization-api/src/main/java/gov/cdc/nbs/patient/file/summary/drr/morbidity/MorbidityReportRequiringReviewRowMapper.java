package gov.cdc.nbs.patient.file.summary.drr.morbidity;

import gov.cdc.nbs.demographics.name.DisplayableSimpleName;
import gov.cdc.nbs.demographics.name.DisplayableSimpleNameRowMapper;
import gov.cdc.nbs.patient.file.summary.drr.DocumentRequiringReview;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

class MorbidityReportRequiringReviewRowMapper implements RowMapper<DocumentRequiringReview> {


  public static final String DOCUMENT_TYPE = "Morbidity Report";


  record Column(
      int identifier,
      int receivedOn,
      int eventDate,
      int reportingFacility,
      int condition,
      int local,
      int electronic,
      DisplayableSimpleNameRowMapper.Columns orderedBy
  ) {

    Column() {
      this(1, 2, 3, 4, 5, 6, 7, new DisplayableSimpleNameRowMapper.Columns(8, 9, 10));
    }

  }


  private final Column columns;
  private final RowMapper<DisplayableSimpleName> providerMapper;

  MorbidityReportRequiringReviewRowMapper() {
    this(new Column());
  }

  MorbidityReportRequiringReviewRowMapper(final Column columns) {
    this.columns = columns;
    this.providerMapper = new DisplayableSimpleNameRowMapper(columns.orderedBy);
  }

  @Override
  public DocumentRequiringReview mapRow(final ResultSet resultSet, int rowNum) throws SQLException {
    long identifier = resultSet.getLong(columns.identifier());
    String local = resultSet.getString(columns.local());
    LocalDateTime receivedOn = resultSet.getObject(columns.receivedOn(), LocalDateTime.class);
    LocalDateTime eventDate = resultSet.getObject(columns.eventDate(), LocalDateTime.class);
    String reportingFacility = resultSet.getString(columns.reportingFacility());

    boolean electronic = resultSet.getBoolean(columns.electronic());

    DisplayableSimpleName orderingProvider = this.providerMapper.mapRow(resultSet, rowNum);

    String condition = resultSet.getString(this.columns.condition());

    return new DocumentRequiringReview(
        identifier,
        local,
        DOCUMENT_TYPE,
        eventDate,
        receivedOn,
        electronic,
        false,
        reportingFacility,
        orderingProvider,
        null,
        condition
    );
  }

}
