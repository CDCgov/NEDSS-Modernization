package gov.cdc.nbs.patient.search;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

@Component
public class PatientSearchResultFinder {

  private static final String QUERY =
      """
      select
          [patient].person_uid                    as [patient],
          [patient].local_id                      as [local],
          [patient].birth_time                    as [birthday],
          [current_gender].code_short_desc_txt    as [gender],
          [patient].[record_status_cd]            as [status]
      from Person [patient]

          left join NBS_SRTE..Code_value_general [current_gender] on
                  [current_gender].code_set_nm = 'SEX'
              and [current_gender].[code] = [patient].[curr_sex_cd]

      where   [patient].person_uid in (:patients)
          and [patient].cd = 'PAT'
      """;

  private final NamedParameterJdbcTemplate template;
  private final RowMapper<PatientSearchResult> mapper;

  PatientSearchResultFinder(final JdbcTemplate template) {
    this.template = new NamedParameterJdbcTemplate(template);
    this.mapper =
        new PatientSearchResultMapper(new PatientSearchResultMapper.Columns(1, 2, 3, 4, 5));
  }

  public Collection<PatientSearchResult> find(final Collection<Long> patients) {

    if (patients.isEmpty()) {
      return List.of();
    }

    SqlParameterSource parameters = new MapSqlParameterSource(Map.of("patients", patients));

    return this.template.query(QUERY, parameters, this.mapper);
  }
}
