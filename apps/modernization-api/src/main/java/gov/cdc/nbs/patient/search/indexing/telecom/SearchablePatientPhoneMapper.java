package gov.cdc.nbs.patient.search.indexing.telecom;

import gov.cdc.nbs.patient.search.SearchablePatient;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

class SearchablePatientPhoneMapper {

  record Column(int number, int extension, int typeCd, int useCd) {}

  private final Column columns;

  SearchablePatientPhoneMapper(Column columns) {
    this.columns = columns;
  }

  SearchablePatient.Phone map(final ResultSet resultSet) throws SQLException {
    String number = resultSet.getString(columns.number());
    String extension = resultSet.getString(columns.extension());
    String typeCd = resultSet.getString(columns.typeCd());
    String useCd = resultSet.getString(columns.useCd());

    return new SearchablePatient.Phone(number, extension, typeCd, useCd);
  }

  Optional<SearchablePatient.Phone> maybeMap(final ResultSet resultSet) throws SQLException {

    String number = resultSet.getString(columns.number());

    if (number == null || number.isEmpty()) {
      return Optional.empty();
    }

    String extension = resultSet.getString(columns.extension());
    String typeCd = resultSet.getString(columns.typeCd());
    String useCd = resultSet.getString(columns.useCd());

    return Optional.of(new SearchablePatient.Phone(number, extension, typeCd, useCd));
  }
}
