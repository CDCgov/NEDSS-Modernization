package gov.cdc.nbs.patient.profile.summary.race;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

@Component
class PatientSummaryRaceFinder {

  private static final String QUERY = """
      select distinct
          [person_race].race_category_cd      as [race_id],
          [race].code_short_desc_txt          as [race_description],
          count(*) over ()                    as [total]
      from Person [patient]
            
          join Person_race [person_race] on
                  [person_race].[person_uid] = [patient].person_uid
              and [person_race].[record_status_cd] = 'ACTIVE'
            
          join NBS_SRTE..Code_value_general [race] on
                  [race].code_set_nm = 'RACE_CALCULATED'
              and [race].[code] = [person_race].race_category_cd
              and [race].concept_status_cd = 'Published'
            
      where [patient].person_uid = ?
          and [patient].cd = 'PAT'
      order by
        [race].code_short_desc_txt
      """;
  private static final int PATIENT_PARAMETER = 1;
  private static final int RACE_CODE_COLUMN = 1;
  private static final int TOTAL_COLUMN = 3;
  private static final int RACE_DESCRIPTION_COLUMN = 2;
  private static final String RACE_UNKNOWN = "U";

  private final JdbcTemplate template;

  PatientSummaryRaceFinder(final JdbcTemplate template) {
    this.template = template;
  }

  Collection<String> find(final long patient) {
    return this.template.query(
            QUERY,
            statement -> statement.setLong(PATIENT_PARAMETER, patient),
            this::map
        ).stream()
        .flatMap(Optional::stream)
        .toList();
  }

  private Optional<String> map(final ResultSet resultSet, final int row) throws SQLException {
    String race = resultSet.getString(RACE_CODE_COLUMN);
    long total = resultSet.getLong(TOTAL_COLUMN);

    if (total > 1 && Objects.equals(race, RACE_UNKNOWN)) {
      return Optional.empty();
    }

    String description = resultSet.getString(RACE_DESCRIPTION_COLUMN);
    return Optional.of(description);
  }

}
