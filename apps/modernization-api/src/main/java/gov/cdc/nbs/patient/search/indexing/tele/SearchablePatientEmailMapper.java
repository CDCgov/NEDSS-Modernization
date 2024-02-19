package gov.cdc.nbs.patient.search.indexing.tele;

import gov.cdc.nbs.patient.search.SearchablePatient;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

class SearchablePatientEmailMapper implements RowMapper<SearchablePatient.Email> {

  record Column(int address) {
  }


  private final Column columns;

  public SearchablePatientEmailMapper(Column columns) {
    this.columns = columns;
  }

  @Override
  public SearchablePatient.Email mapRow(final ResultSet resultSet, final int rowNum) throws SQLException {
    String address = resultSet.getString(columns.address());

    return new SearchablePatient.Email(address);
  }
}
