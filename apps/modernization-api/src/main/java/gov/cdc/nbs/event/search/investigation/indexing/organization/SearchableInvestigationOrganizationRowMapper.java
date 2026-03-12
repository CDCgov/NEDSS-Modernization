package gov.cdc.nbs.event.search.investigation.indexing.organization;

import gov.cdc.nbs.event.search.investigation.SearchableInvestigation;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

class SearchableInvestigationOrganizationRowMapper
    implements RowMapper<SearchableInvestigation.Organization> {

  record Column(int identifier, int type) {}

  private final Column columns;

  SearchableInvestigationOrganizationRowMapper(final Column columns) {
    this.columns = columns;
  }

  @Override
  public SearchableInvestigation.Organization mapRow(final ResultSet resultSet, final int rowNum)
      throws SQLException {
    long identifier = resultSet.getLong(this.columns.identifier());
    String type = resultSet.getString(this.columns.type());

    return new SearchableInvestigation.Organization(identifier, type);
  }
}
