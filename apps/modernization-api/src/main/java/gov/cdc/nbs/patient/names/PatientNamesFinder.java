package gov.cdc.nbs.patient.names;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
class PatientNamesFinder {
  private String selectNamesSql = """
        select
          [name].person_uid,
          [name].as_of_date,
          [name].nm_use_cd,
          [use].[code_short_desc_txt],
          [name].nm_prefix,
          [prefix].[code_short_desc_txt],
          [name].first_nm,
          [name].middle_nm,
          [name].last_nm,
          [name].nm_suffix,
          [name].nm_suffix,
          [name].nm_degree,
          [degree].[code_short_desc_txt]
      from person_name [name]
      join NBS_SRTE..Code_value_general [use] on
                 [use].[code_set_nm] = 'P_NM_USE'
             and [use].[code] = [name].nm_use_cd
      left join NBS_SRTE..Code_value_general [suffix] on
                  [suffix].[code_set_nm] = 'P_NM_SFX'
              and [suffix].[code] = [name].nm_suffix
      left join NBS_SRTE..Code_value_general [degree] on
                  [suffix].[code_set_nm] = 'P_NM_DEG'
              and [suffix].[code] = [name].nm_degree
      left join NBS_SRTE..Code_value_general [prefix] on
                  [suffix].[code_set_nm] = 'P_NM_PFX'
              and [suffix].[code] = [name].nm_prefix
      where
             [name].record_status_cd = 'ACTIVE' and
             [name].person_uid = ?
      """;

  private static final int PERSON_UID_PARAMETER = 1;

  private final JdbcTemplate template;
  private final PatientNamesRowMapper mapper;

  PatientNamesFinder(final JdbcTemplate template) {
    this.template = template;
    this.mapper = new PatientNamesRowMapper();
  }

  List<PatientName> find(final long personUid) {
    return this.template.query(
        selectNamesSql,
        statement -> statement.setLong(PERSON_UID_PARAMETER, personUid),
        this.mapper);
  }
}
