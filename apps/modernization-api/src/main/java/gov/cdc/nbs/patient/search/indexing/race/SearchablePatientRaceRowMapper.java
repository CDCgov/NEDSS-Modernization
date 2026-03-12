package gov.cdc.nbs.patient.search.indexing.race;

import gov.cdc.nbs.patient.search.SearchablePatient;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

class SearchablePatientRaceRowMapper implements RowMapper<SearchablePatient.Race> {

  record Column(int category, int detail) {}

  private final Column columns;

  SearchablePatientRaceRowMapper(final Column columns) {
    this.columns = columns;
  }

  @Override
  public SearchablePatient.Race mapRow(final ResultSet resultSet, final int rowNum)
      throws SQLException {
    String category = resultSet.getString(columns.category());
    String detail = resultSet.getString(columns.detail());

    return new SearchablePatient.Race(category, detail);
  }
}
