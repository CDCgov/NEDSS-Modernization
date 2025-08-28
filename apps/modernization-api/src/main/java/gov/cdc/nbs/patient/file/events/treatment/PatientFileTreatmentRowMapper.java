package gov.cdc.nbs.patient.file.events.treatment;

import gov.cdc.nbs.data.time.LocalDateColumnMapper;
import gov.cdc.nbs.demographics.name.DisplayableSimpleName;
import gov.cdc.nbs.demographics.name.DisplayableSimpleNameRowMapper;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;

class PatientFileTreatmentRowMapper implements RowMapper<PatientFileTreatment> {
  record Column(
      int patient,
      int identifier,
      int local,
      int createdOn,
      int treatedOn,
      int description,
      int organization,
      DisplayableSimpleNameRowMapper.Columns provider
  ) {
    Column() {
      this(
          1, 2, 3, 4, 5, 6, 7,
          new DisplayableSimpleNameRowMapper.Columns(8, 9, 10)
      );
    }
  }


  private final Column columns;
  private final RowMapper<DisplayableSimpleName> providerMapper;

  PatientFileTreatmentRowMapper() {
    this(new Column());
  }

  PatientFileTreatmentRowMapper(final Column columns) {
    this.columns = columns;
    this.providerMapper = new DisplayableSimpleNameRowMapper(columns.provider);
  }

  @Override
  public PatientFileTreatment mapRow(final ResultSet resultSet, final int rowNum) throws SQLException {
    long patient = resultSet.getLong(this.columns.patient());
    long identifier = resultSet.getLong(this.columns.identifier());
    String local = resultSet.getString(this.columns.local());
    LocalDateTime createdOn = resultSet.getObject(this.columns.createdOn, LocalDateTime.class);
    String organization = resultSet.getString(this.columns.organization());
    DisplayableSimpleName provider = this.providerMapper.mapRow(resultSet, rowNum);
    LocalDate treatedOn = LocalDateColumnMapper.map(resultSet, this.columns.treatedOn());
    String description = resultSet.getString(this.columns.description());

    return new PatientFileTreatment(
        patient,
        identifier,
        local,
        createdOn,
        treatedOn,
        description,
        organization,
        provider,
        Collections.emptyList()
    );
  }

}
