package gov.cdc.nbs.patient.documentsrequiringreview.detail;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class LabTestSummaryRowMapper implements RowMapper<LabTestSummary> {

  public record Column(int name, int status, int coded, int numeric, int high, int low, int unit) {}

  private final Column columns;

  public LabTestSummaryRowMapper(final Column columns) {
    this.columns = columns;
  }

  @Override
  public LabTestSummary mapRow(final ResultSet resultSet, final int rowNum) throws SQLException {
    String name = resultSet.getString(columns.name());
    String coded = resultSet.getString(columns.coded());
    String status = resultSet.getString(columns.status());
    BigDecimal numeric = resultSet.getBigDecimal(columns.numeric());
    String high = resultSet.getString(columns.high());
    String low = resultSet.getString(columns.low());
    String unit = resultSet.getString(columns.unit);

    return new LabTestSummary(name, status, coded, numeric, high, low, unit);
  }
}
