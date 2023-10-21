package gov.cdc.nbs.concept;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;

import java.sql.ResultSet;
import java.sql.SQLException;

class ConceptRowMapper implements RowMapper<Concept> {

  record Column(int value, int name, int order) {
    Column() {
      this(1, 2, 3);
    }
  }


  private final Column columns;

  ConceptRowMapper() {
    this(new Column());
  }

  ConceptRowMapper(final Column columns) {
    this.columns = columns;
  }

  @Override
  @NonNull
  public Concept mapRow(final ResultSet rs, final int rowNum) throws SQLException {
    String value = rs.getString(columns.value());
    String name = rs.getString(columns.name());
    int order = rs.getInt(columns.order());
    return new Concept(value, name, order);
  }
}
