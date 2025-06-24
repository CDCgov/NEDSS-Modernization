package gov.cdc.nbs.patient.file.demographics.mortality;

import gov.cdc.nbs.data.selectable.Selectable;
import gov.cdc.nbs.data.selectable.SelectableRowMapper;
import gov.cdc.nbs.data.time.LocalDateColumnMapper;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

class PatientMortalityDemographicRowMapper implements RowMapper<PatientMortalityDemographic> {

  record Column(
      int asOf,
      SelectableRowMapper.Column deceased,
      int deceasedOn,
      int city,
      SelectableRowMapper.Column state,
      SelectableRowMapper.Column county,
      SelectableRowMapper.Column country
  ) {

    Column() {
      this(1,
          new SelectableRowMapper.Column(2, 3),
          4, 5,
          new SelectableRowMapper.Column(6, 7),
          new SelectableRowMapper.Column(8, 9),
          new SelectableRowMapper.Column(10, 11)
      );
    }
  }


  private final Column columns;
  private final SelectableRowMapper deceasedMapper;
  private final SelectableRowMapper stateMapper;
  private final SelectableRowMapper countyMapper;
  private final SelectableRowMapper countryMapper;

  PatientMortalityDemographicRowMapper() {
    this(new Column());
  }

  PatientMortalityDemographicRowMapper(final Column columns) {
    this.columns = columns;

    this.deceasedMapper = new SelectableRowMapper(columns.deceased());
    this.stateMapper = new SelectableRowMapper(columns.state());
    this.countyMapper = new SelectableRowMapper(columns.county());
    this.countryMapper = new SelectableRowMapper(columns.country());
  }

  @Override
  public PatientMortalityDemographic mapRow(final ResultSet resultSet, int rowNum) throws SQLException {

    LocalDate asOf = LocalDateColumnMapper.map(resultSet, columns.asOf());
    Selectable deceased = deceasedMapper.mapRow(resultSet, rowNum);
    LocalDate deceasedOn = LocalDateColumnMapper.map(resultSet, columns.deceasedOn());

    String city = resultSet.getString(columns.city());
    Selectable state = stateMapper.mapRow(resultSet, rowNum);
    Selectable county = countyMapper.mapRow(resultSet, rowNum);
    Selectable country = countryMapper.mapRow(resultSet, rowNum);

    return new PatientMortalityDemographic(
        asOf,
        deceased,
        deceasedOn,
        city,
        state,
        county,
        country
    );
  }
}
