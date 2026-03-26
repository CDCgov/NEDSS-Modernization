package gov.cdc.nbs.patient.file.demographics.identification;

import gov.cdc.nbs.data.selectable.Selectable;
import gov.cdc.nbs.data.selectable.SelectableRowMapper;
import gov.cdc.nbs.data.time.LocalDateColumnMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import org.springframework.jdbc.core.RowMapper;

class PatientIdentificationDemographicRowMapper
    implements RowMapper<PatientIdentificationDemographic> {

  record Column(
      int identifier,
      int asOf,
      SelectableRowMapper.Column type,
      SelectableRowMapper.Column issuer,
      int value) {
    Column() {
      this(1, 2, new SelectableRowMapper.Column(3, 4), new SelectableRowMapper.Column(5, 6), 7);
    }
  }

  private final Column columns;
  private final SelectableRowMapper typeMapper;
  private final SelectableRowMapper issuerMapper;

  PatientIdentificationDemographicRowMapper() {
    this(new Column());
  }

  PatientIdentificationDemographicRowMapper(final Column columns) {
    this.columns = columns;
    this.typeMapper = new SelectableRowMapper(columns.type());
    this.issuerMapper = new SelectableRowMapper(columns.issuer());
  }

  @Override
  public PatientIdentificationDemographic mapRow(final ResultSet resultSet, int rowNum)
      throws SQLException {
    short identifier = resultSet.getShort(columns.identifier());
    LocalDate asOf = LocalDateColumnMapper.map(resultSet, columns.asOf());
    Selectable type = typeMapper.mapRow(resultSet, rowNum);
    Selectable issuer = issuerMapper.mapRow(resultSet, rowNum);

    String value = resultSet.getString(columns.value());

    return new PatientIdentificationDemographic(identifier, asOf, type, issuer, value);
  }
}
