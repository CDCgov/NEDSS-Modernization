package gov.cdc.nbs.patient.search.name;

import java.util.Collection;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
class PatientSearchResultOtherNamesFinder {

  private static final String QUERY =
      """
      select distinct
          coalesce(
              [use].[code_short_desc_txt],
              [name].nm_use_cd
          ) as [type],
          [name].first_nm,
          [name].middle_nm,
          [name].last_nm,
          [suffix].code_short_desc_txt
      from Person_name [name]

          left join NBS_SRTE..Code_value_general [use] on
                 [use].[code_set_nm] = 'P_NM_USE'
             and [use].[code] = [name].nm_use_cd

           left join NBS_SRTE..Code_value_general [suffix] on
                   [suffix].[code_set_nm] = 'P_NM_SFX'
               and [suffix].[code] = [name].nm_suffix

       where   [name].person_uid = ?
           and [name].record_status_cd = 'ACTIVE'
      """;
  private static final int PATIENT_PARAMETER = 1;

  private final JdbcTemplate template;
  private final RowMapper<PatientSearchResultName> mapper;

  PatientSearchResultOtherNamesFinder(final JdbcTemplate template) {
    this.template = template;
    this.mapper =
        new PatientSearchResultNameMapper(new PatientSearchResultNameMapper.Columns(1, 2, 3, 4, 5));
  }

  Collection<PatientSearchResultName> find(final long patient) {
    return this.template.query(
        QUERY, statement -> statement.setLong(PATIENT_PARAMETER, patient), this.mapper);
  }
}
