package gov.cdc.nbs.patient.search.indexing.tele;

import gov.cdc.nbs.patient.search.SearchablePatient;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

class SearchablePatientTeleRowCallbackHandler implements RowCallbackHandler {

  record Column(int type, SearchablePatientEmailMapper.Column email, SearchablePatientPhoneMapper.Column phone) {
  }


  private final Column columns;
  private final RowMapper<SearchablePatient.Email> emailRowMapper;
  private final Collection<SearchablePatient.Email> emails;
  private final RowMapper<SearchablePatient.Phone> phoneRowMapper;
  private final Collection<SearchablePatient.Phone> phones;
  private int row;


  SearchablePatientTeleRowCallbackHandler(final Column columns) {
    this.columns = columns;
    this.emailRowMapper = new SearchablePatientEmailMapper(columns.email());
    this.emails = new ArrayList<>();
    this.phoneRowMapper = new SearchablePatientPhoneMapper(columns.phone());
    this.phones = new ArrayList<>();
    this.row = 0;
  }

  @Override
  public void processRow(final ResultSet resultSet) throws SQLException {

    String type = resultSet.getString(columns.type());
    int current = ++this.row;


    if (Objects.equals(type, "NET")) {
      mapEmail(resultSet, current);
    } else {
      mapPhone(resultSet, current);
    }

  }

  private void mapEmail(final ResultSet resultSet, final int row) throws SQLException {
    SearchablePatient.Email email = this.emailRowMapper.mapRow(resultSet, row);
    this.emails.add(email);
  }

  private void mapPhone(final ResultSet resultSet, final int row) throws SQLException {
    SearchablePatient.Phone phone = this.phoneRowMapper.mapRow(resultSet, row);
    this.phones.add(phone);
  }

  Collection<SearchablePatient.Email> emails() {
    return List.copyOf(this.emails);
  }

  Collection<SearchablePatient.Phone> phones() {
    return List.copyOf(this.phones);
  }

}
