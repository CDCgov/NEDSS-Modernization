package gov.cdc.nbs.demographics.address;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class DisplayableAddressRowMapper implements RowMapper<DisplayableAddress> {

  public record Columns(int use, int address, int address2, int city, int state, int zipcode) {
    public Columns() {
      this(1, 2, 3, 4, 5, 6);
    }
  }

  private final Columns columns;

  public DisplayableAddressRowMapper() {
    this(new Columns());
  }

  public DisplayableAddressRowMapper(final Columns columns) {
    this.columns = columns;
  }

  @Override
  public DisplayableAddress mapRow(final ResultSet resultSet, final int row) throws SQLException {
    String use = resultSet.getString(columns.use());
    String address = resultSet.getString(columns.address());
    String address2 = resultSet.getString(columns.address2());
    String city = resultSet.getString(columns.city());
    String state = resultSet.getString(columns.state());
    String zipcode = resultSet.getString(columns.zipcode());

    return new DisplayableAddress(use, address, address2, city, state, zipcode);
  }
}
