package gov.cdc.nbs.patient.search.name;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

class PatientSearchResultNameMapper implements RowMapper<PatientSearchResultName> {

  record Index(int first, int middle, int last, int suffix) {
  }

  private final Index columns;


  PatientSearchResultNameMapper(final Index columns) {
    this.columns = columns;
  }

  @Override
  public PatientSearchResultName mapRow(final ResultSet resultSet, final int row) throws SQLException {
    String first = resultSet.getString(columns.first());
    String middle = resultSet.getString(columns.middle());
    String last = resultSet.getString(columns.last());
    String suffix = resultSet.getString(columns.suffix());

    return new PatientSearchResultName(
        first,
        middle,
        last,
        suffix
    );
  }
}
