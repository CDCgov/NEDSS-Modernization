package gov.cdc.nbs.patient.names;

import org.springframework.jdbc.core.RowMapper;
import gov.cdc.nbs.data.selectable.Selectable;
import gov.cdc.nbs.data.selectable.SelectableRowMapper;
import gov.cdc.nbs.data.time.LocalDateColumnMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

class PatientNamesRowMapper implements RowMapper<PatientName> {


  record Column(
      int identifier,
      int asOf,
      SelectableRowMapper.Column type,
      SelectableRowMapper.Column prefix,
      int first,
      int middle,
      int last,
      SelectableRowMapper.Column suffix,
      SelectableRowMapper.Column degree) {
    Column() {
      this(1, 2, new SelectableRowMapper.Column(3, 4),
          new SelectableRowMapper.Column(5, 6), 7, 8, 9,
          new SelectableRowMapper.Column(10, 11), new SelectableRowMapper.Column(12, 13));
    }

  }

  private final Column columns;
  private final SelectableRowMapper typeMapper;
  private final SelectableRowMapper suffixMapper;
  private final SelectableRowMapper degreeMapper;
  private final SelectableRowMapper prefixMapper;

  PatientNamesRowMapper() {
    this(new Column());
  }

  PatientNamesRowMapper(final Column columns) {
    this.columns = columns;
    this.typeMapper = new SelectableRowMapper(columns.type());
    this.suffixMapper = new SelectableRowMapper(columns.suffix());
    this.degreeMapper = new SelectableRowMapper(columns.degree());
    this.prefixMapper = new SelectableRowMapper(columns.prefix());
  }


  @Override
  public PatientName mapRow(final ResultSet resultSet, final int rowNum) throws SQLException {
    long identifier = resultSet.getLong(this.columns.identifier());
    LocalDate asOf = LocalDateColumnMapper.map(resultSet, columns.asOf());
    String first = resultSet.getString(this.columns.first());
    String middle = resultSet.getString(this.columns.middle());
    String last = resultSet.getString(this.columns.last());
    Selectable type = typeMapper.mapRow(resultSet, rowNum);
    Selectable suffix = suffixMapper.mapRow(resultSet, rowNum);
    Selectable degree = degreeMapper.mapRow(resultSet, rowNum);
    Selectable prefix = prefixMapper.mapRow(resultSet, rowNum);

    return new PatientName(
        identifier,
        asOf,
        type,
        prefix,
        first,
        middle,
        last,
        suffix,
        degree);
  }
}
