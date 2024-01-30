package gov.cdc.nbs.provider;

import gov.cdc.nbs.patient.NameRenderer;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProviderNameRowMapper {


  public record Column(
      int prefix,
      int first,
      int last,
      int suffix
  ) {
  }


  private final Column columns;

  public ProviderNameRowMapper(final Column columns) {
    this.columns = columns;
  }

  public String map(final ResultSet resultSet) throws SQLException {
    String prefix = resultSet.getString(this.columns.prefix());
    String first = resultSet.getString(this.columns.first());
    String last = resultSet.getString(this.columns.last());
    String suffix = resultSet.getString(this.columns.suffix());

    return NameRenderer.render(
        prefix,
        first,
        last,
        suffix
    );
  }
}
