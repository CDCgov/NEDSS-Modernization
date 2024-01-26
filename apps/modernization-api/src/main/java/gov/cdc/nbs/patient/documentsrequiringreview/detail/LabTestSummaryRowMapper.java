package gov.cdc.nbs.patient.documentsrequiringreview.detail;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LabTestSummaryRowMapper {

  public record Column(
      int name,
      int status,
      int coded,
      int numeric,
      int high,
      int low,
      int unit
  ) {
  }


  private final Column columns;

  public LabTestSummaryRowMapper(final Column columns) {
    this.columns = columns;
  }

  public LabTestSummary map(final ResultSet resultSet) throws SQLException {
    String name = resultSet.getString(columns.name());
    String coded = resultSet.getString(columns.coded());
    String status = resultSet.getString(columns.status());
    BigDecimal numeric = resultSet.getBigDecimal(columns.numeric());
    String high = resultSet.getString(columns.high());
    String low = resultSet.getString(columns.low());
    String unit = resultSet.getString(columns.unit);

    return new LabTestSummary(
        name,
        status,
        coded,
        numeric,
        high,
        low,
        unit
    );
  }
}
