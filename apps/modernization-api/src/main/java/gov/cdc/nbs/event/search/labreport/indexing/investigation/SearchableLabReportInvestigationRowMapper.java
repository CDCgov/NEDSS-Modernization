package gov.cdc.nbs.event.search.labreport.indexing.investigation;

import gov.cdc.nbs.event.search.labreport.SearchableLabReport;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

class SearchableLabReportInvestigationRowMapper
    implements RowMapper<SearchableLabReport.Investigation> {

  record Column(int local, int condition) {}

  private final Column columns;

  SearchableLabReportInvestigationRowMapper(final Column columns) {
    this.columns = columns;
  }

  @Override
  public SearchableLabReport.Investigation mapRow(final ResultSet resultSet, final int rowNum)
      throws SQLException {
    String local = resultSet.getString(this.columns.local());
    String condition = resultSet.getString(this.columns.condition());

    return new SearchableLabReport.Investigation(local, condition);
  }
}
