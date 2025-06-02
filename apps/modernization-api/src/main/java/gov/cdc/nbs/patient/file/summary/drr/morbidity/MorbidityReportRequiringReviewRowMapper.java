package gov.cdc.nbs.patient.file.summary.drr.morbidity;

import gov.cdc.nbs.data.time.LocalDateColumnMapper;
import gov.cdc.nbs.demographics.name.DisplayableSimpleName;
import gov.cdc.nbs.demographics.name.DisplayableSimpleNameRowMapper;
import gov.cdc.nbs.patient.file.summary.drr.DocumentRequiringReview;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

class MorbidityReportRequiringReviewRowMapper implements RowMapper<DocumentRequiringReview> {


  public static final String DOCUMENT_TYPE = "Morbidity Report";


  record Column(
      int patient,
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
      this(1, 2, 3, 4, 5, 6, 7, 8, new DisplayableSimpleNameRowMapper.Columns(9, 10, 11));
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
    long patient = resultSet.getLong(columns.patient());
    long identifier = resultSet.getLong(columns.identifier());
    String local = resultSet.getString(columns.local());
    LocalDateTime receivedOn = resultSet.getObject(columns.receivedOn(), LocalDateTime.class);
    LocalDate eventDate = LocalDateColumnMapper.map(resultSet, columns.eventDate());
    String reportingFacility = resultSet.getString(columns.reportingFacility());

    boolean electronic = resultSet.getBoolean(columns.electronic());

    DisplayableSimpleName orderingProvider = this.providerMapper.mapRow(resultSet, rowNum);

    String condition = resultSet.getString(this.columns.condition());

    return new DocumentRequiringReview(
        patient,
        identifier,
        local,
        DOCUMENT_TYPE,
        eventDate,
        receivedOn,
        electronic,
        false,
        reportingFacility,
        null,
        orderingProvider,
        null,
        condition
    );
  }

}
