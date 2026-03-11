package gov.cdc.nbs.address;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class AddressRowMapper implements RowMapper<Address> {

  public record Columns(
      int type,
      int use,
      int address,
      int address2,
      int city,
      int state,
      int zipcode,
      int country,
      int county) {
    public Columns() {
      this(1, 2, 3, 4, 5, 6, 7, 8, 9);
    }
  }

  private final Columns columns;

  public AddressRowMapper() {
    this(new Columns());
  }

  public AddressRowMapper(final Columns columns) {
    this.columns = columns;
  }

  @Override
  public Address mapRow(final ResultSet resultSet, final int row) throws SQLException {
    String type = resultSet.getString(columns.type());
    String use = resultSet.getString(columns.use());
    String address = resultSet.getString(columns.address());
    String address2 = resultSet.getString(columns.address2());
    String city = resultSet.getString(columns.city());
    String state = resultSet.getString(columns.state());
    String zipcode = resultSet.getString(columns.zipcode());
    String country = resultSet.getString(columns.country());
    String county = resultSet.getString(columns.county());

    return new Address(type, use, address, address2, city, state, zipcode, country, county);
  }
}
