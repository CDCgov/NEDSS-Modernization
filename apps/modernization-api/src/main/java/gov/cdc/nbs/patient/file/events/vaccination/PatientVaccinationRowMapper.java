package gov.cdc.nbs.patient.file.events.vaccination;

import gov.cdc.nbs.data.time.LocalDateColumnMapper;
import gov.cdc.nbs.demographics.name.DisplayableSimpleName;
import gov.cdc.nbs.demographics.name.DisplayableSimpleNameRowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import org.springframework.jdbc.core.RowMapper;

class PatientVaccinationRowMapper implements RowMapper<PatientVaccination> {
  record Column(
      int patient,
      int identifier,
      int local,
      int createdOn,
      int organization,
      DisplayableSimpleNameRowMapper.Columns provider,
      int administeredOn,
      int administered) {
    Column() {
      this(1, 2, 3, 4, 5, new DisplayableSimpleNameRowMapper.Columns(6, 7, 8), 9, 10);
    }
  }

  private final Column columns;
  private final RowMapper<DisplayableSimpleName> providerMapper;

  PatientVaccinationRowMapper() {
    this(new Column());
  }

  PatientVaccinationRowMapper(final Column columns) {
    this.columns = columns;
    this.providerMapper = new DisplayableSimpleNameRowMapper(columns.provider);
  }

  @Override
  public PatientVaccination mapRow(final ResultSet resultSet, final int rowNum)
      throws SQLException {
    long patient = resultSet.getLong(this.columns.patient());
    long identifier = resultSet.getLong(this.columns.identifier());
    String local = resultSet.getString(this.columns.local());
    LocalDateTime createdOn = resultSet.getObject(this.columns.createdOn, LocalDateTime.class);
    String organization = resultSet.getString(this.columns.organization());
    DisplayableSimpleName provider = this.providerMapper.mapRow(resultSet, rowNum);
    LocalDate administeredOn = LocalDateColumnMapper.map(resultSet, this.columns.administeredOn());
    String administered = resultSet.getString(this.columns.administered());

    return new PatientVaccination(
        patient,
        identifier,
        local,
        createdOn,
        organization,
        provider,
        administeredOn,
        administered,
        Collections.emptyList());
  }
}
