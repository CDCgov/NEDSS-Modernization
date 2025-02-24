package gov.cdc.nbs.patient.search.indexing.identification;

import gov.cdc.nbs.patient.search.SearchablePatient;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

class SearchablePatientIdentificationRowMapper implements RowMapper<SearchablePatient.Identification> {

  record Column(int type, int value) {
  }


  private final Column columns;

  SearchablePatientIdentificationRowMapper(final Column columns) {
    this.columns = columns;
  }

  @Override
  public SearchablePatient.Identification mapRow(final ResultSet resultSet, final int rowNum) throws SQLException {
    String type = resultSet.getString(columns.type());
    String value = resultSet.getString(columns.value());

    return new SearchablePatient.Identification(
        type,
        value
    );
  }
}
