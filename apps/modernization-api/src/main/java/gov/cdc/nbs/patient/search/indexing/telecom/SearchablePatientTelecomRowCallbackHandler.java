package gov.cdc.nbs.patient.search.indexing.telecom;

import gov.cdc.nbs.patient.search.SearchablePatient;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.jdbc.core.RowCallbackHandler;

class SearchablePatientTelecomRowCallbackHandler implements RowCallbackHandler {

  private final SearchablePatientEmailMapper emailRowMapper;
  private final Collection<SearchablePatient.Email> emails;
  private final SearchablePatientPhoneMapper phoneRowMapper;
  private final Collection<SearchablePatient.Phone> phones;

  SearchablePatientTelecomRowCallbackHandler(
      final SearchablePatientEmailMapper.Column email,
      final SearchablePatientPhoneMapper.Column phone) {
    this.emailRowMapper = new SearchablePatientEmailMapper(email);
    this.emails = new ArrayList<>();
    this.phoneRowMapper = new SearchablePatientPhoneMapper(phone);
    this.phones = new ArrayList<>();
  }

  @Override
  public void processRow(final ResultSet resultSet) throws SQLException {
    this.emailRowMapper.maybeMap(resultSet).ifPresent(this.emails::add);
    this.phoneRowMapper.maybeMap(resultSet).ifPresent(this.phones::add);
  }

  SearchablePatientTelecom telecom() {
    return new SearchablePatientTelecom(List.copyOf(this.phones), List.copyOf(this.emails));
  }
}
