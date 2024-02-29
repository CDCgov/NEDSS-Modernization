package gov.cdc.nbs.patient.search.indexing.address;

import gov.cdc.nbs.patient.search.SearchablePatient;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

class SearchablePatientAddressRowMapper implements RowMapper<SearchablePatient.Address> {

  record Column(int address1, int address2, int city, int state, int zip, int county, int country) {}


  private final Column columns;

  SearchablePatientAddressRowMapper(final Column columns) {
    this.columns = columns;
  }

  @Override
  public SearchablePatient.Address mapRow(final ResultSet resultSet, final int rowNum) throws SQLException {
    String address1 = resultSet.getString(this.columns.address1());
    String address2 = resultSet.getString(this.columns.address2());
    String city = resultSet.getString(this.columns.city());
    String state = resultSet.getString(this.columns.state());
    String zip = resultSet.getString(this.columns.zip());
    String county = resultSet.getString(this.columns.county());
    String country = resultSet.getString(this.columns.country());
    return new SearchablePatient.Address(
        address1,
        address2,
        city,
        state,
        zip,
        county,
        country
    );
  }
}
