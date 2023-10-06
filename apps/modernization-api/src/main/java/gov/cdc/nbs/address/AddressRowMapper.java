package gov.cdc.nbs.address;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AddressRowMapper implements RowMapper<Address> {

  public record Columns(int use, int address, int address2, int city, int state, int zipcode, int country) {
    public Columns() {
      this(1, 2, 3, 4, 5, 6, 7);
    }
  }


  private final Columns columns;


  public AddressRowMapper(final Columns columns) {
    this.columns = columns;
  }

  @Override
  public Address mapRow(final ResultSet resultSet, final int row) throws SQLException {
    String use = resultSet.getString(columns.use());
    String address = resultSet.getString(columns.address());
    String address2 = resultSet.getString(columns.address2());
    String city = resultSet.getString(columns.city());
    String state = resultSet.getString(columns.state());
    String zipcode = resultSet.getString(columns.zipcode());
    String country = resultSet.getString(columns.country());

    return new Address(
        use,
        address,
        address2,
        city,
        state,
        zipcode,
        country
    );
  }
}
