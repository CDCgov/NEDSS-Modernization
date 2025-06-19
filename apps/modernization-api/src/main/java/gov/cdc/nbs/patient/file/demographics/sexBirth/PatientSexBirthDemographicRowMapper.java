package gov.cdc.nbs.patient.file.demographics.sexBirth;

import gov.cdc.nbs.data.selectable.Selectable;
import gov.cdc.nbs.data.selectable.SelectableRowMapper;
import gov.cdc.nbs.data.time.LocalDateColumnMapper;
import gov.cdc.nbs.sql.IntegerColumnMapper;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

class PatientSexBirthDemographicRowMapper implements RowMapper<PatientSexBirthDemographic> {

  record Column(
      int asOf,
      int bornOn,
      SelectableRowMapper.Column sex,
      SelectableRowMapper.Column multiple,
      int order,
      int city,
      SelectableRowMapper.Column state,
      SelectableRowMapper.Column county,
      SelectableRowMapper.Column country,
      //
      SelectableRowMapper.Column current,
      SelectableRowMapper.Column unknownReason,
      SelectableRowMapper.Column transgenderInformation,
      int additionalGender
  ) {
    Column() {
      this(1, 2,
          new SelectableRowMapper.Column(3, 4),
          new SelectableRowMapper.Column(5, 6),
          7, 8,
          new SelectableRowMapper.Column(9, 10),
          new SelectableRowMapper.Column(11, 12),
          new SelectableRowMapper.Column(13, 14),
          //
          new SelectableRowMapper.Column(15, 16),
          new SelectableRowMapper.Column(17, 18),
          new SelectableRowMapper.Column(19, 20),
          21
      );
    }
  }


  private final Column columns;
  private final SelectableRowMapper sexMapper;
  private final SelectableRowMapper multipleMapper;
  private final SelectableRowMapper stateMapper;
  private final SelectableRowMapper countyMapper;
  private final SelectableRowMapper countryMapper;
  //
  private final SelectableRowMapper currentMapper;
  private final SelectableRowMapper unknownReasonMapper;
  private final SelectableRowMapper transgenderInformationMapper;

  PatientSexBirthDemographicRowMapper() {
    this(new Column());
  }

  PatientSexBirthDemographicRowMapper(final Column columns) {
    this.columns = columns;
    this.sexMapper = new SelectableRowMapper(columns.sex());
    this.multipleMapper = new SelectableRowMapper(columns.multiple());
    this.stateMapper = new SelectableRowMapper(columns.state());
    this.countyMapper = new SelectableRowMapper(columns.county());
    this.countryMapper = new SelectableRowMapper(columns.country());
    //
    this.currentMapper = new SelectableRowMapper(columns.current());
    this.unknownReasonMapper = new SelectableRowMapper(columns.unknownReason());
    this.transgenderInformationMapper = new SelectableRowMapper(columns.transgenderInformation());
  }

  @Override
  public PatientSexBirthDemographic mapRow(final ResultSet resultSet, int rowNum) throws SQLException {

    LocalDate asOf = LocalDateColumnMapper.map(resultSet, columns.asOf());
    LocalDate bornOn = LocalDateColumnMapper.map(resultSet, columns.bornOn());
    Selectable sex = sexMapper.mapRow(resultSet, rowNum);
    Selectable multiple = multipleMapper.mapRow(resultSet, rowNum);
    Integer order = IntegerColumnMapper.map(resultSet, columns.order());
    String city = resultSet.getString(columns.city());
    Selectable state = stateMapper.mapRow(resultSet, rowNum);
    Selectable county = countyMapper.mapRow(resultSet, rowNum);
    Selectable country = countryMapper.mapRow(resultSet, rowNum);

    //
    Selectable current = currentMapper.mapRow(resultSet, rowNum);
    Selectable unknownReason = unknownReasonMapper.mapRow(resultSet, rowNum);
    Selectable transgenderInformation = transgenderInformationMapper.mapRow(resultSet, rowNum);
    String additionalGender = resultSet.getString(columns.additionalGender());

    return new PatientSexBirthDemographic(
        asOf,
        bornOn,
        sex,
        multiple,
        order,
        city,
        state,
        county,
        country,
        current,
        unknownReason,
        transgenderInformation,
        additionalGender
    );
  }
}

