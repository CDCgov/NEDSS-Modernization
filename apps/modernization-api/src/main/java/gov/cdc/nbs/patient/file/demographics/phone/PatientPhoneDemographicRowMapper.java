package gov.cdc.nbs.patient.file.demographics.phone;

import gov.cdc.nbs.data.selectable.Selectable;
import gov.cdc.nbs.data.selectable.SelectableRowMapper;
import gov.cdc.nbs.data.time.LocalDateColumnMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import org.springframework.jdbc.core.RowMapper;

class PatientPhoneDemographicRowMapper implements RowMapper<PatientPhoneDemographic> {

  record Column(
      int identifier,
      int asOf,
      SelectableRowMapper.Column type,
      SelectableRowMapper.Column use,
      int countryCode,
      int phoneNumber,
      int extension,
      int email,
      int url,
      int comment) {
    Column() {
      this(
          1,
          2,
          new SelectableRowMapper.Column(3, 4),
          new SelectableRowMapper.Column(5, 6),
          7,
          8,
          9,
          10,
          11,
          12);
    }
  }

  private final Column columns;
  private final SelectableRowMapper typeMapper;
  private final SelectableRowMapper useMapper;

  PatientPhoneDemographicRowMapper() {
    this(new Column());
  }

  PatientPhoneDemographicRowMapper(final Column columns) {
    this.columns = columns;
    this.typeMapper = new SelectableRowMapper(columns.type());
    this.useMapper = new SelectableRowMapper(columns.use());
  }

  @Override
  public PatientPhoneDemographic mapRow(final ResultSet resultSet, int rowNum) throws SQLException {
    long identifier = resultSet.getLong(columns.identifier());
    LocalDate asOf = LocalDateColumnMapper.map(resultSet, columns.asOf());
    Selectable type = typeMapper.mapRow(resultSet, rowNum);
    Selectable use = useMapper.mapRow(resultSet, rowNum);

    String countryCode = resultSet.getString(columns.countryCode());
    String phoneNumber = resultSet.getString(columns.phoneNumber());
    String extension = resultSet.getString(columns.extension());
    String email = resultSet.getString(columns.email());
    String url = resultSet.getString(columns.url());
    String comment = resultSet.getString(columns.comment());

    return new PatientPhoneDemographic(
        identifier, asOf, type, use, countryCode, phoneNumber, extension, email, url, comment);
  }
}
