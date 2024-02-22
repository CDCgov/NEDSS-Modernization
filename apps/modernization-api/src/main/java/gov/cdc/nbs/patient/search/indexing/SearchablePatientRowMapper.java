package gov.cdc.nbs.patient.search.indexing;

import gov.cdc.nbs.data.time.LocalDateColumnMapper;
import gov.cdc.nbs.patient.search.SearchablePatient;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

class SearchablePatientRowMapper implements RowMapper<SearchablePatient> {

  record Column(int identifier, int local, int status, int birthday, int deceased, int gender, int ethnicity) {
  }


  private final Column columns;

  SearchablePatientRowMapper(final Column columns) {
    this.columns = columns;
  }

  @Override
  public SearchablePatient mapRow(final ResultSet resultSet, int rowNum) throws SQLException {
    long identifier = resultSet.getLong(columns.identifier());
    String local = resultSet.getString(columns.local());
    String status = resultSet.getString(columns.status());
    LocalDate birthday = LocalDateColumnMapper.map(resultSet, columns.birthday());
    String deceased = resultSet.getString(columns.deceased());
    String gender = resultSet.getString(columns.gender());
    String ethnicity = resultSet.getString(columns.ethnicity());

    return new SearchablePatient(
        identifier,
        local,
        status,
        birthday,
        deceased,
        gender,
        ethnicity
    );
  }
}
