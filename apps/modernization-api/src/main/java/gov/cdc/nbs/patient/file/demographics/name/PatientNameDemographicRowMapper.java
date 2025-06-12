package gov.cdc.nbs.patient.file.demographics.name;

import gov.cdc.nbs.data.selectable.Selectable;
import gov.cdc.nbs.data.selectable.SelectableRowMapper;
import gov.cdc.nbs.data.time.LocalDateColumnMapper;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

class PatientNameDemographicRowMapper implements RowMapper<PatientNameDemographic> {


  record Column(
      int identifier,
      int asOf,
      SelectableRowMapper.Column type,
      SelectableRowMapper.Column prefix,
      int first,
      int middle,
      int secondMiddle,
      int last,
      int secondLast,
      SelectableRowMapper.Column suffix,
      SelectableRowMapper.Column degree
  ) {
    Column() {
      this(
          1,
          2,
          new SelectableRowMapper.Column(3, 4),
          new SelectableRowMapper.Column(5, 6),
          7, 8, 9, 10, 11,
          new SelectableRowMapper.Column(12, 13),
          new SelectableRowMapper.Column(14, 15)
      );
    }

  }


  private final Column columns;
  private final SelectableRowMapper typeMapper;
  private final SelectableRowMapper prefixMapper;
  private final SelectableRowMapper suffixMapper;
  private final SelectableRowMapper degreeMapper;

  PatientNameDemographicRowMapper() {
    this(new Column());
  }

  PatientNameDemographicRowMapper(final Column columns) {
    this.columns = columns;
    this.typeMapper = new SelectableRowMapper(columns.type());
    this.prefixMapper = new SelectableRowMapper(columns.prefix());
    this.suffixMapper = new SelectableRowMapper(columns.suffix());
    this.degreeMapper = new SelectableRowMapper(columns.degree());
  }


  @Override
  public PatientNameDemographic mapRow(final ResultSet resultSet, final int rowNum) throws SQLException {
    long identifier = resultSet.getLong(this.columns.identifier());
    LocalDate asOf = LocalDateColumnMapper.map(resultSet, columns.asOf());
    Selectable prefix = prefixMapper.mapRow(resultSet, rowNum);
    String first = resultSet.getString(this.columns.first());
    String middle = resultSet.getString(this.columns.middle());
    String secondMiddle = resultSet.getString(this.columns.secondMiddle());
    String last = resultSet.getString(this.columns.last());
    String secondLast = resultSet.getString(this.columns.secondLast());
    Selectable type = typeMapper.mapRow(resultSet, rowNum);
    Selectable suffix = suffixMapper.mapRow(resultSet, rowNum);
    Selectable degree = degreeMapper.mapRow(resultSet, rowNum);

    return new PatientNameDemographic(
        identifier,
        asOf,
        type,
        prefix,
        first,
        middle,
        secondMiddle,
        last,
        secondLast,
        suffix,
        degree
    );
  }
}
