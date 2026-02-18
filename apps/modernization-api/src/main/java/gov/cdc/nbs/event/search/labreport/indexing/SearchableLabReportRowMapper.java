package gov.cdc.nbs.event.search.labreport.indexing;

import gov.cdc.nbs.data.time.LocalDateColumnMapper;
import gov.cdc.nbs.event.search.labreport.SearchableLabReport;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import org.springframework.jdbc.core.RowMapper;

class SearchableLabReportRowMapper implements RowMapper<SearchableLabReport> {

  record Column(
      int identifier,
      int classCode,
      int mood,
      int programArea,
      int jurisdiction,
      int oid,
      int pregnancyStatus,
      int local,
      int reportedOn,
      int collectedOn,
      int receivedOn,
      int createdBy,
      int createdOn,
      int updatedBy,
      int updatedOn,
      int version,
      int status,
      int electronicEntry) {}

  private final Column columns;

  SearchableLabReportRowMapper(final Column columns) {
    this.columns = columns;
  }

  @Override
  public SearchableLabReport mapRow(final ResultSet resultSet, final int rowNum)
      throws SQLException {
    long identifier = resultSet.getLong(this.columns.identifier());
    String classCode = resultSet.getString(this.columns.classCode());
    String mood = resultSet.getString(this.columns.mood());
    String programArea = resultSet.getString(this.columns.programArea());
    String jurisdiction = resultSet.getString(this.columns.jurisdiction());
    long oid = resultSet.getLong(this.columns.oid());
    String pregnancyStatus = resultSet.getString(this.columns.pregnancyStatus());
    String local = resultSet.getString(this.columns.local());

    LocalDate reportedOn = LocalDateColumnMapper.map(resultSet, this.columns.reportedOn());
    LocalDate collectedOn = LocalDateColumnMapper.map(resultSet, this.columns.collectedOn());
    LocalDate receivedOn = LocalDateColumnMapper.map(resultSet, this.columns.receivedOn());

    long createdBy = resultSet.getLong(this.columns.createdBy());
    LocalDate createdOn = LocalDateColumnMapper.map(resultSet, this.columns.createdOn());

    long updatedBy = resultSet.getLong(this.columns.updatedBy());
    LocalDate updatedOn = LocalDateColumnMapper.map(resultSet, this.columns.updatedOn());

    long version = resultSet.getLong(this.columns.version());
    String status = resultSet.getString(this.columns.status());
    String electronicEntry = resultSet.getString(this.columns.electronicEntry());

    return new SearchableLabReport(
        identifier,
        classCode,
        mood,
        programArea,
        jurisdiction,
        oid,
        pregnancyStatus,
        local,
        reportedOn,
        collectedOn,
        receivedOn,
        createdBy,
        createdOn,
        updatedBy,
        updatedOn,
        version,
        status,
        electronicEntry);
  }
}
