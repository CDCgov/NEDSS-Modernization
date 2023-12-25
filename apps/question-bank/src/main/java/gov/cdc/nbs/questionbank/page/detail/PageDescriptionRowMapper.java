package gov.cdc.nbs.questionbank.page.detail;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;

import java.sql.ResultSet;
import java.sql.SQLException;


class PageDescriptionRowMapper implements RowMapper<PageDescription> {

  record Column(int identifier, int name, int status, int description) {
    Column() {
      this(1, 2, 3,4);
    }
  }


  private final Column columns;

  PageDescriptionRowMapper() {
    this(new Column());
  }

  PageDescriptionRowMapper(final Column columns) {
    this.columns = columns;
  }

  @Override
  @NonNull
  public PageDescription mapRow(final ResultSet rs, final int rowNum) throws SQLException {
    long identifier = rs.getLong(columns.identifier());
    String name = rs.getString(columns.name());
    String status = rs.getString(columns.status());
    String description = rs.getString(columns.description());
    return new PageDescription(
        identifier,
        name,
        status,
        description
    );
  }

}
