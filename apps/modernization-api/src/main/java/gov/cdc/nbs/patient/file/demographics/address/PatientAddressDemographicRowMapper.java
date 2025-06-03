package gov.cdc.nbs.patient.file.demographics.address;

import gov.cdc.nbs.data.selectable.Selectable;
import gov.cdc.nbs.data.selectable.SelectableRowMapper;
import gov.cdc.nbs.data.time.LocalDateColumnMapper;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

class PatientAddressDemographicRowMapper implements RowMapper<PatientAddressDemographic> {

  record Column(
      int identifier,
      int asOf,
      SelectableRowMapper.Column type,
      SelectableRowMapper.Column use,
      int address1,
      int address2,
      int city,
      SelectableRowMapper.Column county,
      SelectableRowMapper.Column state,
      int zipcode,
      SelectableRowMapper.Column country,
      int censusTract,
      int comment
  ) {
    Column() {
      this(
          1,
          2,
          new SelectableRowMapper.Column(3, 4),
          new SelectableRowMapper.Column(5, 6),
          7,
          8,
          9,
          new SelectableRowMapper.Column(10, 11),
          new SelectableRowMapper.Column(12, 13),
          14,
          new SelectableRowMapper.Column(15, 16),
          17,
          18
      );
    }
  }


  private final Column columns;
  private final SelectableRowMapper typeMapper;
  private final SelectableRowMapper useMapper;
  private final SelectableRowMapper stateMapper;
  private final SelectableRowMapper countyMapper;
  private final SelectableRowMapper countryMapper;

  PatientAddressDemographicRowMapper() {
    this(new Column());
  }

  PatientAddressDemographicRowMapper(final Column columns) {
    this.columns = columns;
    this.typeMapper = new SelectableRowMapper(columns.type());
    this.useMapper = new SelectableRowMapper(columns.use());
    this.stateMapper = new SelectableRowMapper(columns.state());
    this.countyMapper = new SelectableRowMapper(columns.county());
    this.countryMapper = new SelectableRowMapper(columns.country());
  }

  @Override
  public PatientAddressDemographic mapRow(final ResultSet resultSet, int rowNum) throws SQLException {
    long identifier = resultSet.getLong(columns.identifier());
    LocalDate asOf = LocalDateColumnMapper.map(resultSet, columns.asOf());
    Selectable type = typeMapper.mapRow(resultSet, rowNum);
    Selectable use = useMapper.mapRow(resultSet, rowNum);

    String address1 = resultSet.getString(columns.address1());
    String address2 = resultSet.getString(columns.address2());
    String city = resultSet.getString(columns.city());
    Selectable state = stateMapper.mapRow(resultSet, rowNum);
    String zip = resultSet.getString(columns.zipcode());
    Selectable county = countyMapper.mapRow(resultSet, rowNum);
    String censusTract = resultSet.getString(columns.censusTract());
    Selectable country = countryMapper.mapRow(resultSet, rowNum);

    String comment = resultSet.getString(columns.comment());

    return new PatientAddressDemographic(
        identifier,
        asOf,
        type,
        use,
        address1,
        address2,
        city,
        state,
        zip,
        county,
        censusTract,
        country,
        comment
    );
  }
}
