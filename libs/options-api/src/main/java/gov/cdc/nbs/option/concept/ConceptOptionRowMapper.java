package gov.cdc.nbs.option.concept;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;

public class ConceptOptionRowMapper implements RowMapper<ConceptOption> {

  public record Column(int value, int name, int order) {
    Column() {
      this(1, 2, 3);
    }
  }

  private final Column columns;

  public ConceptOptionRowMapper() {
    this(new Column());
  }

  public ConceptOptionRowMapper(final Column columns) {
    this.columns = columns;
  }

  @Override
  @NonNull public ConceptOption mapRow(final ResultSet rs, final int rowNum) throws SQLException {
    String value = rs.getString(columns.value());
    String name = rs.getString(columns.name());
    int order = rs.getInt(columns.order());
    return new ConceptOption(value, name, order);
  }
}
