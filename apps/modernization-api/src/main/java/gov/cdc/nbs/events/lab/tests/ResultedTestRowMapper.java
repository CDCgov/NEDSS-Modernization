package gov.cdc.nbs.events.lab.tests;

import org.springframework.jdbc.core.RowMapper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

class ResultedTestRowMapper implements RowMapper<ResultedTest> {

  public record Column(
      int name,
      int status,
      int coded,
      int numeric,
      int high,
      int low,
      int unit
  ) {
    public Column() {
      this(1, 2, 3, 4, 5, 6, 7);
    }
  }


  private final Column columns;

  public ResultedTestRowMapper() {
    this(new Column());
  }

  public ResultedTestRowMapper(final Column columns) {
    this.columns = columns;
  }

  @Override
  public ResultedTest mapRow(final ResultSet resultSet, int rowNum) throws SQLException {
    String name = resultSet.getString(columns.name());
    String coded = resultSet.getString(columns.coded());
    String status = resultSet.getString(columns.status());
    BigDecimal numeric = resultSet.getBigDecimal(columns.numeric());
    String high = resultSet.getString(columns.high());
    String low = resultSet.getString(columns.low());
    String unit = resultSet.getString(columns.unit);

    return new ResultedTest(
        name,
        status,
        coded,
        numeric,
        high,
        low,
        unit);
  }
}

