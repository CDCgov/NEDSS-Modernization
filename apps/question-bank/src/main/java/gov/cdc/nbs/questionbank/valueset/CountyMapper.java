package gov.cdc.nbs.questionbank.valueset;

import gov.cdc.nbs.questionbank.valueset.response.County;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

class CountyMapper implements RowMapper<County> {
  record Column(int code, int shortDescription, int description, int codeSetName) {}

  private final Column columns;

  CountyMapper() {
    this.columns = new Column(1, 2, 3, 4);
  }

  @Override
  public County mapRow(final ResultSet rs, final int rowNum) throws SQLException {
    String code = rs.getString(columns.code);
    String shortDescription = rs.getString(columns.shortDescription);
    String description = rs.getString(columns.description);
    String codeSetName = rs.getString(columns.codeSetName);
    return new County(code, shortDescription, description, codeSetName);
  }
}
