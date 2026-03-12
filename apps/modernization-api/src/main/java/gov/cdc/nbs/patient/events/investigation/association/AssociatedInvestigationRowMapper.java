package gov.cdc.nbs.patient.events.investigation.association;

import gov.cdc.nbs.sql.LongColumnMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class AssociatedInvestigationRowMapper implements RowMapper<AssociatedInvestigation> {

  public record Column(int identifier, int local, int condition, int status) {}

  private final Column columns;

  public AssociatedInvestigationRowMapper(final Column columns) {
    this.columns = columns;
  }

  @Override
  public AssociatedInvestigation mapRow(final ResultSet resultSet, final int rowNum)
      throws SQLException {

    Long identifier = LongColumnMapper.map(resultSet, columns.identifier());

    if (identifier != null) {
      String local = resultSet.getString(columns.local);
      String condition = resultSet.getString(columns.condition);
      String status = resultSet.getString(columns.status);

      return new AssociatedInvestigation(identifier, local, condition, status);
    }

    return null;
  }
}
