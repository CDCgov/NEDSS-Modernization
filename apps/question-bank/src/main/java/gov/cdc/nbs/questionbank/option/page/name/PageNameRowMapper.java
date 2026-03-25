package gov.cdc.nbs.questionbank.option.page.name;

import gov.cdc.nbs.questionbank.option.PageBuilderOption;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;

public class PageNameRowMapper implements RowMapper<PageBuilderOption> {

  public record Column(int value) {
    Column() {
      this(1);
    }
  }

  private final Column columns;

  public PageNameRowMapper() {
    this(new Column());
  }

  public PageNameRowMapper(final Column columns) {
    this.columns = columns;
  }

  @Override
  @NonNull public PageBuilderOption mapRow(final ResultSet rs, final int rowNum) throws SQLException {
    String value = rs.getString(columns.value());
    return new PageBuilderOption(value, value, rowNum);
  }
}
