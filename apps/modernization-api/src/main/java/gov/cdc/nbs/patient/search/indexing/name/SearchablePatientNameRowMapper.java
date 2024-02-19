package gov.cdc.nbs.patient.search.indexing.name;

import gov.cdc.nbs.patient.search.SearchablePatient;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

class SearchablePatientNameRowMapper implements RowMapper<SearchablePatient.Name> {

  record Column(
      int use,
      int first,
      int firstSoundex,
      int middle,
      int last,
      int lastSoundex,
      int prefix,
      int suffix
  ) {
  }


  private final Column column;

  public SearchablePatientNameRowMapper(final Column column) {
    this.column = column;
  }

  @Override
  public SearchablePatient.Name mapRow(final ResultSet resultSet, final int rowNum) throws SQLException {
    String use = resultSet.getString(column.use());
    String first = resultSet.getString(column.first());
    String firstSoundex = resultSet.getString(column.firstSoundex());
    String middle = resultSet.getString(column.middle());
    String last = resultSet.getString(column.last());
    String lastSoundex = resultSet.getString(column.lastSoundex());
    String prefix = resultSet.getString(column.prefix());
    String suffix = resultSet.getString(column.suffix());

    return new SearchablePatient.Name(
        use,
        first,
        firstSoundex,
        middle,
        last,
        lastSoundex,
        prefix,
        suffix
    );
  }

}
