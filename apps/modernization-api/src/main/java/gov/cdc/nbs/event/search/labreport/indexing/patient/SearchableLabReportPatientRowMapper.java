package gov.cdc.nbs.event.search.labreport.indexing.patient;

import gov.cdc.nbs.data.time.LocalDateColumnMapper;
import gov.cdc.nbs.event.search.labreport.SearchableLabReport;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

class SearchableLabReportPatientRowMapper implements RowMapper<SearchableLabReport.Person.Patient> {

  record Column(
      int identifier,
      int local,
      int type,
      int subjectType,
      int firstName,
      int lastName,
      int birthday
  ) {
  }


  private final Column columns;

  SearchableLabReportPatientRowMapper(final Column columns) {
    this.columns = columns;
  }

  @Override
  public SearchableLabReport.Person.Patient mapRow(
      final ResultSet resultSet,
      final int rowNum
  ) throws SQLException {


    long identifier = resultSet.getLong(this.columns.identifier());
    String local = resultSet.getString(this.columns.local());
    String type = resultSet.getString(this.columns.type());
    String subjectType = resultSet.getString(this.columns.subjectType());
    String first = resultSet.getString(this.columns.firstName());
    String last = resultSet.getString(this.columns.lastName());
    LocalDate birthday = LocalDateColumnMapper.map(resultSet, columns.birthday());

    return new SearchableLabReport.Person.Patient(
        identifier,
        local,
        type,
        subjectType,
        first,
        last,
        birthday
    );
  }
}
