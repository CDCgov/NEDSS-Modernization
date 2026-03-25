package gov.cdc.nbs.event.search.investigation.indexing.identifier;

import gov.cdc.nbs.event.search.investigation.SearchableInvestigation;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

class SearchableInvestigationIdentifierRowMapper
    implements RowMapper<SearchableInvestigation.Identifier> {

  record Column(int sequence, int type, int value) {}

  private final Column columns;

  SearchableInvestigationIdentifierRowMapper(final Column columns) {
    this.columns = columns;
  }

  @Override
  public SearchableInvestigation.Identifier mapRow(final ResultSet resultSet, final int rowNum)
      throws SQLException {
    int sequence = resultSet.getInt(this.columns.sequence());
    String type = resultSet.getString(this.columns.type());
    String value = resultSet.getString(this.columns.value());

    return new SearchableInvestigation.Identifier(sequence, type, value);
  }
}
