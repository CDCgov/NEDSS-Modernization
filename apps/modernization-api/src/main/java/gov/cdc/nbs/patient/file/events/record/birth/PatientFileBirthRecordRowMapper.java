package gov.cdc.nbs.patient.file.events.record.birth;

import gov.cdc.nbs.data.time.LocalDateColumnMapper;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

class PatientFileBirthRecordRowMapper implements RowMapper<PatientFileBirthRecord> {


  private static final String MOTHER_FIRST_NAME_QUESTION = "MTH201";
  private static final String MOTHER_MIDDLE_NAME_QUESTION = "MTH202";
  private static final String MOTHER_LAST_NAME_QUESTION = "MTH203";
  private static final String MOTHER_SUFFIX_QUESTION = "MTH204";
  private static final String MOTHER_STREET_ADDRESS_QUESTION = "DEM159_MTH";
  private static final String MOTHER_STREET_ADDRESS_2_QUESTION = "DEM160_MTH";
  private static final String MOTHER_CITY_QUESTION = "MTH209";
  private static final String MOTHER_STATE_QUESTION = "MTH166";
  private static final String MOTHER_COUNTY_QUESTION = "MTH168";
  private static final String MOTHER_ZIP_CODE_QUESTION = "MTH169";


  record Column(
      int patient,
      int identifier,
      int local,
      int receivedOn,
      int facility,
      int collectedOn,
      int certificate,
      int question,
      int answer
  ) {
    Column() {
      this(1, 2, 3, 4, 5, 6, 7, 8, 9);
    }
  }


  private final Column columns;

  PatientFileBirthRecordRowMapper() {
    this(new Column());
  }

  PatientFileBirthRecordRowMapper(final Column columns) {
    this.columns = columns;
  }

  @Override
  public PatientFileBirthRecord mapRow(final ResultSet resultSet, final int rowNum) throws SQLException {

    long patient = resultSet.getLong(this.columns.patient);
    long identifier = resultSet.getLong(this.columns.identifier);
    String local = resultSet.getString(this.columns.local);
    LocalDateTime receivedOn = resultSet.getObject(this.columns.receivedOn, LocalDateTime.class);
    String facility = resultSet.getString(this.columns.facility);
    LocalDate collectedOn = LocalDateColumnMapper.map(resultSet, this.columns.collectedOn);
    String certificate = resultSet.getString(this.columns.certificate);

    PatientFileBirthRecord.MotherInformation mother = mapMother(resultSet);

    return new PatientFileBirthRecord(
        patient,
        identifier,
        local,
        receivedOn,
        facility,
        collectedOn,
        certificate,
        mother
    );
  }

  private PatientFileBirthRecord.MotherInformation mapMother(final ResultSet resultSet) throws SQLException {
    String question = resultSet.getString(this.columns.question);
    String answer = resultSet.getString(this.columns.answer);

    if (question != null) {
      PatientFileBirthRecord.MotherInformation.Name name = mapName(question, answer);
      PatientFileBirthRecord.MotherInformation.Address address = mapAddress(question, answer);

      return new PatientFileBirthRecord.MotherInformation(name, address);
    }

    return null;
  }

  private PatientFileBirthRecord.MotherInformation.Name mapName(final String question, final String answer) {
    String first = Objects.equals(question, MOTHER_FIRST_NAME_QUESTION) ? answer : null;
    String middle = Objects.equals(question, MOTHER_MIDDLE_NAME_QUESTION) ? answer : null;
    String last = Objects.equals(question, MOTHER_LAST_NAME_QUESTION) ? answer : null;
    String suffix = Objects.equals(question, MOTHER_SUFFIX_QUESTION) ? answer : null;
    return new PatientFileBirthRecord.MotherInformation.Name(first, middle, last, suffix);
  }

  private PatientFileBirthRecord.MotherInformation.Address mapAddress(final String question, final String answer) {
    String address = Objects.equals(question, MOTHER_STREET_ADDRESS_QUESTION) ? answer : null;
    String address2 = Objects.equals(question, MOTHER_STREET_ADDRESS_2_QUESTION) ? answer : null;
    String city = Objects.equals(question, MOTHER_CITY_QUESTION) ? answer : null;
    String state = Objects.equals(question, MOTHER_STATE_QUESTION) ? answer : null;
    String county = Objects.equals(question, MOTHER_COUNTY_QUESTION) ? answer : null;
    String zipcode = Objects.equals(question, MOTHER_ZIP_CODE_QUESTION) ? answer : null;
    return new PatientFileBirthRecord.MotherInformation.Address(address, address2, city, state, county, zipcode);
  }
}
