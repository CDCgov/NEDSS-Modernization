package gov.cdc.nbs.event.search.labreport.indexing.identifier;

import gov.cdc.nbs.event.search.labreport.SearchableLabReport;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

class SearchableLabReportIdentifierRowMapper implements RowMapper<SearchableLabReport.Identifier> {

  record Column(int type, int description, int value) {}

  private final Column columns;

  SearchableLabReportIdentifierRowMapper(final Column columns) {
    this.columns = columns;
  }

  @Override
  public SearchableLabReport.Identifier mapRow(final ResultSet resultSet, final int rowNum)
      throws SQLException {
    String type = resultSet.getString(this.columns.type());
    String description = resultSet.getString(this.columns.description());
    String value = resultSet.getString(this.columns.value());

    return new SearchableLabReport.Identifier(type, description, value);
  }
}
