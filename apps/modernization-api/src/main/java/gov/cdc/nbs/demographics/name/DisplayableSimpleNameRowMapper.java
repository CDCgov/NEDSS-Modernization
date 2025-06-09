package gov.cdc.nbs.demographics.name;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DisplayableSimpleNameRowMapper implements RowMapper<DisplayableSimpleName> {

  public record Columns(int prefix, int first, int last) {
    public Columns(int first, int last) {
      this(-1, first, last);
    }
  }


  private final Columns columns;

  public DisplayableSimpleNameRowMapper(final Columns columns) {
    this.columns = columns;
  }

  @Override
  public DisplayableSimpleName mapRow(final ResultSet resultSet, final int row) throws SQLException {
    String prefix = maybeMapPrefix(resultSet);
    String first = resultSet.getString(columns.first());
    String last = resultSet.getString(columns.last());

    if (prefix == null && first == null && last == null) {
      return null;
    }

    return new DisplayableSimpleName(
        prefix,
        first,
        last
    );
  }

  private String maybeMapPrefix(final ResultSet resultSet) throws SQLException {
    return columns.prefix() > 0 ? resultSet.getString(columns.prefix()) : null;
  }
}
