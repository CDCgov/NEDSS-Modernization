package gov.cdc.nbs.patient.search.indexing.name;

import gov.cdc.nbs.patient.search.SearchablePatient;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.commons.codec.language.Soundex;
import org.springframework.jdbc.core.RowMapper;

class SearchablePatientNameRowMapper implements RowMapper<SearchablePatient.Name> {

  record Column(int use, int first, int middle, int last, int prefix, int suffix, int full) {}

  private final Column column;
  private final Soundex soundex;

  public SearchablePatientNameRowMapper(final Column column) {
    this.column = column;
    this.soundex = new Soundex();
  }

  @Override
  public SearchablePatient.Name mapRow(final ResultSet resultSet, final int rowNum)
      throws SQLException {
    String use = resultSet.getString(column.use());
    String first = resultSet.getString(column.first());
    String firstSoundex = encoded(first);
    String middle = resultSet.getString(column.middle());
    String last = resultSet.getString(column.last());
    String lastSoundex = encoded(last);
    String prefix = resultSet.getString(column.prefix());
    String suffix = resultSet.getString(column.suffix());
    String full = resultSet.getString(column.full());

    return new SearchablePatient.Name(
        use, first, firstSoundex, middle, last, lastSoundex, prefix, suffix, full);
  }

  private String encoded(final String value) {
    return value == null ? null : this.soundex.encode(value);
  }
}
