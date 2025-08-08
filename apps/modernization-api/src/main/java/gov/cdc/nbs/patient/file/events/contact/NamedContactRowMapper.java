package gov.cdc.nbs.patient.file.events.contact;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

class NamedContactRowMapper implements RowMapper<NamedContact> {

  record Columns(int patientId, int first, int middle, int last, int suffix) {
  }

  private final Columns columns;

  NamedContactRowMapper(final Columns columns) {
    this.columns = columns;
  }

  @Override
  public NamedContact mapRow(final ResultSet resultSet, final int row) throws SQLException {
    long patientId = resultSet.getLong(columns.patientId());
    String first = resultSet.getString(columns.first());
    String middle = resultSet.getString(columns.middle());
    String last = resultSet.getString(columns.last());
    String suffix = resultSet.getString(columns.suffix());

    return new NamedContact(
        patientId,
        first,
        middle,
        last,
        suffix
    );
  }
}
