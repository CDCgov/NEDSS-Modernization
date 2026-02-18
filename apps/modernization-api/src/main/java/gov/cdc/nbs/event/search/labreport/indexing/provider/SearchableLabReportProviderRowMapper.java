package gov.cdc.nbs.event.search.labreport.indexing.provider;

import gov.cdc.nbs.event.search.labreport.SearchableLabReport;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

class SearchableLabReportProviderRowMapper
    implements RowMapper<SearchableLabReport.Person.Provider> {

  record Column(int identifier, int type, int subjectType, int firstName, int lastName) {}

  private final Column columns;

  SearchableLabReportProviderRowMapper(final Column columns) {
    this.columns = columns;
  }

  @Override
  public SearchableLabReport.Person.Provider mapRow(final ResultSet resultSet, final int rowNum)
      throws SQLException {

    long identifier = resultSet.getLong(this.columns.identifier());
    String type = resultSet.getString(this.columns.type());
    String subjectType = resultSet.getString(this.columns.subjectType());
    String first = resultSet.getString(this.columns.firstName());
    String last = resultSet.getString(this.columns.lastName());

    return new SearchableLabReport.Person.Provider(identifier, type, subjectType, first, last);
  }
}
