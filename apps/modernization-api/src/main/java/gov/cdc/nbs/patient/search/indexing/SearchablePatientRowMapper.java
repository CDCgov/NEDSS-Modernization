package gov.cdc.nbs.patient.search.indexing;

import gov.cdc.nbs.data.time.LocalDateColumnMapper;
import gov.cdc.nbs.patient.search.SearchablePatient;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

class SearchablePatientRowMapper implements RowMapper<SearchablePatient> {

  record Column(
      int identifier,
      int local,
      int status,
      int birthday,
      int deceased,
      int gender,
      int ethnicity,
      int documentIds,
      int morbidityReportIds,
      int treatmentIds,
      int vaccinationIds,
      int abcsCaseIds,
      int cityCaseIds,
      int stateCaseIds,
      int accessionIds,
      int investigationIds,
      int labReportIds,
      int notificationIds,
      int sortEmail) {
  }


  private final Column columns;

  SearchablePatientRowMapper(final Column columns) {
    this.columns = columns;
  }

  @Override
  public SearchablePatient mapRow(final ResultSet resultSet, int rowNum) throws SQLException {
    long identifier = resultSet.getLong(columns.identifier());
    String local = resultSet.getString(columns.local());
    String status = resultSet.getString(columns.status());
    LocalDate birthday = LocalDateColumnMapper.map(resultSet, columns.birthday());
    String deceased = resultSet.getString(columns.deceased());
    String gender = resultSet.getString(columns.gender());
    String ethnicity = resultSet.getString(columns.ethnicity());
    String sortEmail = resultSet.getString(columns.sortEmail());
    String documentIds = resultSet.getString(columns.documentIds());
    String morbidityReportIds = resultSet.getString(columns.morbidityReportIds());
    String treatmentIds = resultSet.getString(columns.treatmentIds());
    String vaccinationIds = resultSet.getString(columns.vaccinationIds());
    String stateCaseIds = resultSet.getString(columns.stateCaseIds());
    String abcsCaseIds = resultSet.getString(columns.abcsCaseIds());
    String cityCaseIds = resultSet.getString(columns.cityCaseIds());
    String accessionIds = resultSet.getString(columns.accessionIds());
    String investigationIds = resultSet.getString(columns.investigationIds());
    String labReportIds = resultSet.getString(columns.labReportIds());
    String notificationIds = resultSet.getString(columns.notificationIds());


    return new SearchablePatient(
        identifier,
        local,
        status,
        birthday,
        deceased,
        gender,
        ethnicity,
        documentIds,
        morbidityReportIds,
        treatmentIds,
        vaccinationIds,
        abcsCaseIds,
        cityCaseIds,
        stateCaseIds,
        accessionIds,
        investigationIds,
        labReportIds,
        notificationIds,
        sortEmail);
  }
}
