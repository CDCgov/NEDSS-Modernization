package gov.cdc.nbs.questionbank.page.detail;

import gov.cdc.nbs.questionbank.page.PageStatus;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;

class PageDescriptionRowMapper implements RowMapper<PageDescription> {

  record Column(int identifier, int name, int status, int description, int publishVersionNumber) {
    Column() {
      this(1, 2, 3, 4, 5);
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
  @NonNull public PageDescription mapRow(final ResultSet rs, final int rowNum) throws SQLException {
    long identifier = rs.getLong(columns.identifier());
    String name = rs.getString(columns.name());
    String status = rs.getString(columns.status());
    String publishVersionNumber = rs.getString(columns.publishVersionNumber());
    String description = rs.getString(columns.description());
    if ("Draft".equalsIgnoreCase(status)) {
      status =
          (publishVersionNumber == null)
              ? PageStatus.INITIAL_DRAFT.display()
              : PageStatus.PUBLISHED_WITH_DRAFT.display();
    }
    return new PageDescription(identifier, name, status, description);
  }
}
