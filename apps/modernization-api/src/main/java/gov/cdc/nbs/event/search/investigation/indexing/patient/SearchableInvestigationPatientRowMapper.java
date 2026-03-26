package gov.cdc.nbs.event.search.investigation.indexing.patient;

import gov.cdc.nbs.data.time.LocalDateColumnMapper;
import gov.cdc.nbs.event.search.investigation.SearchableInvestigation;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import org.springframework.jdbc.core.RowMapper;

class SearchableInvestigationPatientRowMapper
    implements RowMapper<SearchableInvestigation.Person.Patient> {

  record Column(
      int identifier,
      int local,
      int type,
      int subjectType,
      int firstName,
      int lastName,
      int gender,
      int birthday) {}

  private final Column columns;

  SearchableInvestigationPatientRowMapper(final Column columns) {
    this.columns = columns;
  }

  @Override
  public SearchableInvestigation.Person.Patient mapRow(final ResultSet resultSet, final int rowNum)
      throws SQLException {

    long identifier = resultSet.getLong(this.columns.identifier());
    String local = resultSet.getString(this.columns.local());
    String type = resultSet.getString(this.columns.type());
    String subjectType = resultSet.getString(this.columns.subjectType());
    String first = resultSet.getString(this.columns.firstName());
    String last = resultSet.getString(this.columns.lastName());
    String gender = resultSet.getString(this.columns.gender());
    LocalDate birthday = LocalDateColumnMapper.map(resultSet, columns.birthday());

    return new SearchableInvestigation.Person.Patient(
        identifier, local, type, subjectType, first, last, gender, birthday);
  }
}
