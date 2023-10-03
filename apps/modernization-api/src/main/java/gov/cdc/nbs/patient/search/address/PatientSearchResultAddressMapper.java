package gov.cdc.nbs.patient.search.address;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

class PatientSearchResultAddressMapper implements RowMapper<PatientSearchResultAddress> {

  record Index(int use, int address, int address2, int city, int state, int zipcode) {
  }


  private final Index columns;


  PatientSearchResultAddressMapper(final Index columns) {
    this.columns = columns;
  }

  @Override
  public PatientSearchResultAddress mapRow(final ResultSet resultSet, final int row) throws SQLException {
    String use = resultSet.getString(columns.use());
    String address = resultSet.getString(columns.address());
    String address2 = resultSet.getString(columns.address2());
    String city = resultSet.getString(columns.city());
    String state = resultSet.getString(columns.state());
    String zipcode = resultSet.getString(columns.zipcode());

    return new PatientSearchResultAddress(
        use,
        address,
        address2,
        city,
        state,
        zipcode
    );
  }
}
