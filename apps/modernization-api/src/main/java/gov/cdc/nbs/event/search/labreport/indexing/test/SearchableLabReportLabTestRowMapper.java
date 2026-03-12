package gov.cdc.nbs.event.search.labreport.indexing.test;

import gov.cdc.nbs.event.search.labreport.SearchableLabReport;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

class SearchableLabReportLabTestRowMapper implements RowMapper<SearchableLabReport.LabTest> {

  record Column(int name, int result, int alternative) {}

  private final Column columns;

  SearchableLabReportLabTestRowMapper(final Column columns) {
    this.columns = columns;
  }

  @Override
  public SearchableLabReport.LabTest mapRow(final ResultSet resultSet, final int rowNum)
      throws SQLException {
    String name = resultSet.getString(this.columns.name());
    String result = resultSet.getString(this.columns.result());
    String alternative = resultSet.getString(this.columns.alternative());

    return new SearchableLabReport.LabTest(name, result, alternative);
  }
}
