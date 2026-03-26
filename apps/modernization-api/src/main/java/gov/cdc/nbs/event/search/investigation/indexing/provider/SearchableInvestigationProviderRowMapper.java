package gov.cdc.nbs.event.search.investigation.indexing.provider;

import gov.cdc.nbs.event.search.investigation.SearchableInvestigation;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

class SearchableInvestigationProviderRowMapper
    implements RowMapper<SearchableInvestigation.Person.Provider> {

  record Column(int identifier, int type, int firstName, int lastName) {}

  private final Column columns;

  SearchableInvestigationProviderRowMapper(final Column columns) {
    this.columns = columns;
  }

  @Override
  public SearchableInvestigation.Person.Provider mapRow(final ResultSet resultSet, final int rowNum)
      throws SQLException {

    long identifier = resultSet.getLong(this.columns.identifier());
    String type = resultSet.getString(this.columns.type());
    String first = resultSet.getString(this.columns.firstName());
    String last = resultSet.getString(this.columns.lastName());

    return new SearchableInvestigation.Person.Provider(identifier, type, first, last);
  }
}
