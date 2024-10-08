package gov.cdc.nbs.event.search.investigation.indexing;

import gov.cdc.nbs.data.time.LocalDateColumnMapper;
import gov.cdc.nbs.event.search.investigation.SearchableInvestigation;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

class SearchableInvestigationRowMapper implements RowMapper<SearchableInvestigation> {

  record Column(
      int identifier,
      int classCode,
      int mood,
      int programArea,
      int jurisdiction,
      int jurisdictionName,
      int oid,
      int caseClass,
      int caseType,
      int outbreak,
      int conditionName,
      int condition,
      int pregnancyStatus,
      int local,
      int createdBy,
      int createdOn,
      int updatedBy,
      int updatedOn,
      int reportedOn,
      int startedOn,
      int closedOn,
      int processing,
      int status,
      int notification,
      int notifiedOn,
      int notificationStatus,
      int investigatorLastName

  ) {
  }

  private final Column columns;

  SearchableInvestigationRowMapper(final Column columns) {
    this.columns = columns;
  }

  @Override
  public SearchableInvestigation mapRow(final ResultSet resultSet, final int rowNum) throws SQLException {
    long identifier = resultSet.getLong(this.columns.identifier());
    String classCode = resultSet.getString(this.columns.classCode());
    String mood = resultSet.getString(this.columns.mood());

    String programArea = resultSet.getString(this.columns.programArea());
    String jurisdiction = resultSet.getString(this.columns.jurisdiction());
    String jurisdictionName = resultSet.getString(this.columns.jurisdictionName());
    long oid = resultSet.getLong(this.columns.oid());

    String caseClass = resultSet.getString(this.columns.caseClass());
    String caseType = resultSet.getString(this.columns.caseType());
    String outbreak = resultSet.getString(this.columns.outbreak());
    String condition = resultSet.getString(this.columns.condition());
    String conditionName = resultSet.getString(this.columns.conditionName());
    String pregnancyStatus = resultSet.getString(this.columns.pregnancyStatus());
    String local = resultSet.getString(this.columns.local());

    long createdBy = resultSet.getLong(this.columns.createdBy());
    LocalDate createdOn = LocalDateColumnMapper.map(resultSet, this.columns.createdOn());

    long updatedBy = resultSet.getLong(this.columns.updatedBy());
    LocalDate updatedOn = LocalDateColumnMapper.map(resultSet, this.columns.updatedOn());

    LocalDate reportedOn = LocalDateColumnMapper.map(resultSet, this.columns.reportedOn());
    LocalDate startedOn = LocalDateColumnMapper.map(resultSet, this.columns.startedOn());
    LocalDate closedOn = LocalDateColumnMapper.map(resultSet, this.columns.closedOn());

    String processing = resultSet.getString(this.columns.processing());
    String status = resultSet.getString(this.columns.status());

    String notification = resultSet.getString(this.columns.notification());
    String notificationStatus = resultSet.getString(this.columns.notificationStatus());
    LocalDate notifiedOn = LocalDateColumnMapper.map(resultSet, this.columns.notifiedOn());

    String investigatorLastName = resultSet.getString(this.columns.investigatorLastName());

    return new SearchableInvestigation(
        identifier,
        classCode,
        mood,
        programArea,
        jurisdiction,
        jurisdictionName,
        oid,
        caseClass,
        caseType,
        outbreak,
        conditionName,
        condition,
        pregnancyStatus,
        local,
        createdBy,
        createdOn,
        updatedBy,
        updatedOn,
        reportedOn,
        startedOn,
        closedOn,
        processing,
        status,
        notification,
        notifiedOn,
        notificationStatus,
        investigatorLastName);
  }
}
