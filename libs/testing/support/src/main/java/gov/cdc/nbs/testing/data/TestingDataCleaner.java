package gov.cdc.nbs.testing.data;

import org.springframework.jdbc.core.simple.JdbcClient;

import java.util.ArrayList;
import java.util.Collection;

public class TestingDataCleaner<I> {

  private final JdbcClient client;
  private final String sql;
  private final String parameter;
  private final Collection<I> identifiers;

  public TestingDataCleaner(
      final JdbcClient client,
      final String sql,
      final String parameter
  ) {
    this.client = client;
    this.sql = sql;
    this.parameter = parameter;
    this.identifiers = new ArrayList<>();
  }

  public void include(final I identifier) {
    identifiers.add(identifier);
  }

  public void clean() {
    if (!identifiers.isEmpty()) {
      this.client.sql(sql)
          .param(parameter, identifiers)
          .update();
    }
  }
}
