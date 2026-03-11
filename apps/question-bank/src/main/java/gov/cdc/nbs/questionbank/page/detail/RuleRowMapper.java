package gov.cdc.nbs.questionbank.page.detail;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;

class RuleRowMapper implements RowMapper<PagesRule> {

  record Column(int id, int page, int logic, int values, int function, int source, int target) {
    Column() {
      this(1, 2, 3, 4, 5, 6, 7);
    }
  }

  private final Column columns;

  RuleRowMapper() {
    this(new Column());
  }

  RuleRowMapper(final Column columns) {
    this.columns = columns;
  }

  @Override
  @NonNull public PagesRule mapRow(final ResultSet resultSet, int row) throws SQLException {
    long id = resultSet.getLong(this.columns.id());
    long page = resultSet.getLong(this.columns.page());
    String logic = resultSet.getString(this.columns.logic());
    String values = resultSet.getString(this.columns.values());
    String function = resultSet.getString(this.columns.function());
    String source = resultSet.getString(this.columns.source());
    String target = resultSet.getString(this.columns.target());
    return new PagesRule(id, page, logic, values, function, source, target);
  }
}
