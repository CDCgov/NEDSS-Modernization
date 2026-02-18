package gov.cdc.nbs.patient.search.indexing.address;

import gov.cdc.nbs.patient.search.SearchablePatient;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

class SearchablePatientAddressRowMapper implements RowMapper<SearchablePatient.Address> {

  record Column(
      int address1,
      int address2,
      int city,
      int state,
      int zip,
      int county,
      int country,
      int countyText,
      int stateText,
      int countryText,
      int full) {}

  private final Column columns;

  SearchablePatientAddressRowMapper(final Column columns) {
    this.columns = columns;
  }

  @Override
  public SearchablePatient.Address mapRow(final ResultSet resultSet, final int rowNum)
      throws SQLException {
    String address1 = resultSet.getString(this.columns.address1());
    String address2 = resultSet.getString(this.columns.address2());
    String city = resultSet.getString(this.columns.city());
    String state = resultSet.getString(this.columns.state());
    String zip = resultSet.getString(this.columns.zip());
    String county = resultSet.getString(this.columns.county());
    String country = resultSet.getString(this.columns.country());
    String countyText = resultSet.getString(this.columns.countyText());
    String stateText = resultSet.getString(this.columns.stateText());
    String countryText = resultSet.getString(this.columns.countryText());
    String full = resultSet.getString(this.columns.full());
    return new SearchablePatient.Address(
        address1,
        address2,
        city,
        state,
        zip,
        county,
        country,
        countyText,
        stateText,
        countryText,
        full);
  }
}
