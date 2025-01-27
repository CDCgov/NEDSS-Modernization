package gov.cdc.nbs.patient.profile.identification;

import gov.cdc.nbs.data.time.LocalDateColumnMapper;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

class PatientIdentificationRowMapper implements RowMapper<PatientIdentification> {

  record Column(
      int patient,
      int sequence,
      int version,
      int asOf,
      int typeId,
      int typeDescription,
      int authorityId,
      int authorityDescription,
      int value
  ) {
  }


  private final Column columns;

  PatientIdentificationRowMapper(final Column columns) {
    this.columns = columns;
  }

  @Override
  public PatientIdentification mapRow(
      final ResultSet resultSet,
      final int rowNum
  ) throws SQLException {

    long patient = resultSet.getLong(this.columns.patient());

    short version = resultSet.getShort(this.columns.version());

    short sequence = resultSet.getShort(this.columns.sequence());

    LocalDate asOf = LocalDateColumnMapper.map(resultSet, this.columns.asOf());

    PatientIdentification.Type type = mapType(resultSet);

    PatientIdentification.Authority authority = maybeMapAuthority(resultSet);

    String value = resultSet.getString(this.columns.value());

    return new PatientIdentification(
        patient,
        sequence,
        version,
        asOf,
        type,
        authority,
        value
    );
  }

  private PatientIdentification.Type mapType(final ResultSet resultSet) throws SQLException {
    String id = resultSet.getString(this.columns.typeId());
    String description = resultSet.getString(this.columns.typeDescription());

    return new PatientIdentification.Type(
        id,
        description
    );
  }

  private PatientIdentification.Authority maybeMapAuthority(final ResultSet resultSet) throws SQLException {
    String id = resultSet.getString(this.columns.authorityId());
    String description = resultSet.getString(this.columns.authorityDescription());

    return id == null
        ? null
        : resolveAuthority(id, description);
  }

  private PatientIdentification.Authority resolveAuthority(final String id, final String description) {
    return new PatientIdentification.Authority(
        id,
        description == null ? id : description
    );
  }
}
