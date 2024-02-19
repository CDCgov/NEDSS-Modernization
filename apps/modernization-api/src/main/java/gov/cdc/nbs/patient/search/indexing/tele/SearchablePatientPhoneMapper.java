package gov.cdc.nbs.patient.search.indexing.tele;

import gov.cdc.nbs.patient.search.SearchablePatient;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

class SearchablePatientPhoneMapper implements RowMapper<SearchablePatient.Phone> {

  record Column(int number, int extension) {
  }

  private final Column columns;

  public SearchablePatientPhoneMapper(Column columns) {
    this.columns = columns;
  }

  @Override
  public SearchablePatient.Phone mapRow(final ResultSet resultSet, final int rowNum) throws SQLException {
    String number = resultSet.getString(columns.number());
    String extension = resultSet.getString(columns.extension());

    return new SearchablePatient.Phone(
        number,
        extension
    );
  }
}
