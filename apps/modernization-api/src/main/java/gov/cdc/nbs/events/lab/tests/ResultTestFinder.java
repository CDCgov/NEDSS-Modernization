package gov.cdc.nbs.events.lab.tests;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

@Component
public class ResultTestFinder {

  private static final String QUERY = """
      """;

  private final JdbcClient client;
  private final RowMapper<ResultedTest> rowMapper;

  public ResultTestFinder(final JdbcClient client) {
    this.client = client;
    this.rowMapper = new ResultedTestRowMapper();
  }

  
}
