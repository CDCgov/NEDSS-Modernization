package gov.cdc.nbs.patient.file.demographics.general;

import gov.cdc.nbs.authorization.permission.Permission;
import gov.cdc.nbs.data.selectable.Selectable;
import gov.cdc.nbs.data.selectable.SelectableRowMapper;
import gov.cdc.nbs.data.sensitive.SensitiveValue;
import gov.cdc.nbs.data.sensitive.SensitiveValueResolver;
import gov.cdc.nbs.data.time.LocalDateColumnMapper;
import gov.cdc.nbs.sql.IntegerColumnMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import org.springframework.jdbc.core.RowMapper;

class PatientGeneralInformationDemographicRowMapper
    implements RowMapper<PatientGeneralInformationDemographic> {

  private static final Permission HIV_PERMISSION = new Permission("HIVQuestions", "Global");

  record Column(
      int asOf,
      SelectableRowMapper.Column maritalStatus,
      int maternalMaidenName,
      int adultsInResidence,
      int childrenInResidence,
      SelectableRowMapper.Column primaryOccupation,
      SelectableRowMapper.Column educationLevel,
      SelectableRowMapper.Column primaryLanguage,
      SelectableRowMapper.Column speaksEnglish,
      int stateHIVCase) {
    Column() {
      this(
          1,
          new SelectableRowMapper.Column(2, 3),
          4,
          5,
          6,
          new SelectableRowMapper.Column(7, 8),
          new SelectableRowMapper.Column(9, 10),
          new SelectableRowMapper.Column(11, 12),
          new SelectableRowMapper.Column(13, 14),
          15);
    }
  }

  private final Column columns;
  private final SelectableRowMapper maritalStatusMapper;
  private final SelectableRowMapper primaryOccupationMapper;
  private final SelectableRowMapper educationLevelMapper;
  private final SelectableRowMapper primaryLanguageMapper;
  private final SelectableRowMapper speaksEnglishMapper;
  private final SensitiveValueResolver resolver;

  PatientGeneralInformationDemographicRowMapper(final SensitiveValueResolver resolver) {
    this(resolver, new Column());
  }

  PatientGeneralInformationDemographicRowMapper(
      final SensitiveValueResolver resolver, final Column columns) {
    this.resolver = resolver;

    this.columns = columns;
    this.maritalStatusMapper = new SelectableRowMapper(columns.maritalStatus());
    this.primaryOccupationMapper = new SelectableRowMapper(columns.primaryOccupation());
    this.educationLevelMapper = new SelectableRowMapper(columns.educationLevel());
    this.primaryLanguageMapper = new SelectableRowMapper(columns.primaryLanguage());
    this.speaksEnglishMapper = new SelectableRowMapper(columns.speaksEnglish());
  }

  @Override
  public PatientGeneralInformationDemographic mapRow(final ResultSet resultSet, int rowNum)
      throws SQLException {

    LocalDate asOf = LocalDateColumnMapper.map(resultSet, columns.asOf());
    Selectable maritalStatus = maritalStatusMapper.mapRow(resultSet, rowNum);
    String maternalMaidenName = resultSet.getString(columns.maternalMaidenName());
    Integer adultsInResidence = IntegerColumnMapper.map(resultSet, columns.adultsInResidence());
    Integer childrenInResidence = IntegerColumnMapper.map(resultSet, columns.childrenInResidence());
    Selectable primaryOccupation = primaryOccupationMapper.mapRow(resultSet, rowNum);
    Selectable educationLevel = educationLevelMapper.mapRow(resultSet, rowNum);
    Selectable primaryLanguage = primaryLanguageMapper.mapRow(resultSet, rowNum);
    Selectable speaksEnglish = speaksEnglishMapper.mapRow(resultSet, rowNum);
    SensitiveValue stateHIVCase = resolveStateHIVCase(resultSet);

    return new PatientGeneralInformationDemographic(
        asOf,
        maritalStatus,
        maternalMaidenName,
        adultsInResidence,
        childrenInResidence,
        primaryOccupation,
        educationLevel,
        primaryLanguage,
        speaksEnglish,
        stateHIVCase);
  }

  private SensitiveValue resolveStateHIVCase(final ResultSet resultSet) throws SQLException {
    return this.resolver.resolve(HIV_PERMISSION, resultSet.getString(columns.stateHIVCase()));
  }
}
