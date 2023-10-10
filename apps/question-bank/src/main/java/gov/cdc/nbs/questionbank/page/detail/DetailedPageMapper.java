package gov.cdc.nbs.questionbank.page.detail;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;

import java.sql.ResultSet;
import java.sql.SQLException;


class DetailedPageMapper implements RowMapper<DetailedPage> {

  record Column(int identifier, int name, int description) {
    Column() {
      this(1, 2, 3);
    }
  }


  private final Column columns;

  DetailedPageMapper() {
    this(new Column());
  }

  DetailedPageMapper(final Column columns) {
    this.columns = columns;
  }

  @Override
  @NonNull
  public DetailedPage mapRow(final ResultSet rs, final int rowNum) throws SQLException {
    long identifier = rs.getLong(columns.identifier());
    String name = rs.getString(columns.name());
    String description = rs.getString(columns.description());
    return new DetailedPage(
        identifier,
        name,
        description
    );
  }

}
