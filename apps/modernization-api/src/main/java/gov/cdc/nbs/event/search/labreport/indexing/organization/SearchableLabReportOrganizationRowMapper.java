package gov.cdc.nbs.event.search.labreport.indexing.organization;

import gov.cdc.nbs.event.search.labreport.SearchableLabReport;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

class SearchableLabReportOrganizationRowMapper
    implements RowMapper<SearchableLabReport.Organization> {

  record Column(int identifier, int type, int subjectType, int name) {}

  private final Column columns;

  SearchableLabReportOrganizationRowMapper(final Column columns) {
    this.columns = columns;
  }

  @Override
  public SearchableLabReport.Organization mapRow(final ResultSet resultSet, final int rowNum)
      throws SQLException {
    long identifier = resultSet.getLong(this.columns.identifier());
    String type = resultSet.getString(this.columns.type());
    String subjectType = resultSet.getString(this.columns.subjectType());
    String name = resultSet.getString(this.columns.name());

    return new SearchableLabReport.Organization(identifier, type, subjectType, name);
  }
}
