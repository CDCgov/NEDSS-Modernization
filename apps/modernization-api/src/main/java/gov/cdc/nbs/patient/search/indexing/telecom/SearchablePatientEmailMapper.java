package gov.cdc.nbs.patient.search.indexing.telecom;

import gov.cdc.nbs.patient.search.SearchablePatient;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

class SearchablePatientEmailMapper {

  record Column(int address) {}

  private final Column columns;

  public SearchablePatientEmailMapper(final Column columns) {
    this.columns = columns;
  }

  SearchablePatient.Email map(final ResultSet resultSet) throws SQLException {
    String address = resultSet.getString(columns.address());

    return new SearchablePatient.Email(address);
  }

  Optional<SearchablePatient.Email> maybeMap(final ResultSet resultSet) throws SQLException {
    String address = resultSet.getString(columns.address());

    return address == null || address.isEmpty()
        ? Optional.empty()
        : Optional.of(new SearchablePatient.Email(address));
  }
}
