package gov.cdc.nbs.patient.file.summary.drr.laboratory;

import gov.cdc.nbs.data.time.LocalDateColumnMapper;
import gov.cdc.nbs.demographics.name.DisplayableSimpleName;
import gov.cdc.nbs.demographics.name.DisplayableSimpleNameRowMapper;
import gov.cdc.nbs.patient.file.summary.drr.DocumentRequiringReview;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

class LaboratoryReportRequiringReviewRowMapper implements RowMapper<DocumentRequiringReview> {


  public static final String DOCUMENT_TYPE = "Laboratory Report";


  record Column(
      int patient,
      int identifier,
      int local,
      int receivedOn,
      int eventDate,
      int electronic,
      int reportingFacility,
      int orderingFacility,
      DisplayableSimpleNameRowMapper.Columns orderedBy
  ) {

    Column() {
      this(1, 2, 3, 4, 5, 6, 7, 8, new DisplayableSimpleNameRowMapper.Columns(9, 10, 11));
    }

  }


  private final Column columns;
  private final RowMapper<DisplayableSimpleName> providerMapper;

  LaboratoryReportRequiringReviewRowMapper() {
    this(new Column());
  }

  LaboratoryReportRequiringReviewRowMapper(final Column columns) {
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

    String orderingFacility = resultSet.getString(this.columns.orderingFacility());

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
        orderingFacility,
        orderingProvider,
        null,
        null
    );
  }

}
