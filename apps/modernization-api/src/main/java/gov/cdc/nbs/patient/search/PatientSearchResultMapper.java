package gov.cdc.nbs.patient.search;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Clock;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import org.springframework.jdbc.core.RowMapper;

class PatientSearchResultMapper implements RowMapper<PatientSearchResult> {

  record Columns(int patient, int local, int birthday, int gender, int status) {}

  private final Clock clock;
  private final Columns columns;

  public PatientSearchResultMapper(final Clock clock, final Columns columns) {
    this.clock = clock;
    this.columns = columns;
  }

  PatientSearchResultMapper(final Columns columns) {
    this(Clock.systemDefaultZone(), columns);
  }

  @Override
  public PatientSearchResult mapRow(final ResultSet resultSet, final int row) throws SQLException {
    long patient = resultSet.getLong(columns.patient());
    String local = resultSet.getString(columns.local());
    LocalDate birthday = resolveBirthdate(resultSet);
    Integer age = resolveAge(birthday);
    String gender = resultSet.getString(columns.gender());
    String status = resultSet.getString(columns.status());

    return new PatientSearchResult(patient, local, birthday, age, gender, status);
  }

  private LocalDate resolveBirthdate(final ResultSet resultSet) throws SQLException {
    Timestamp value = resultSet.getTimestamp(columns.birthday());

    return value == null ? null : value.toLocalDateTime().toLocalDate();
  }

  private Integer resolveAge(final LocalDate birthday) {
    LocalDate now = LocalDate.now(this.clock);

    return birthday == null ? null : (int) ChronoUnit.YEARS.between(birthday, now);
  }
}
