package gov.cdc.nbs.patient.search.indexing.name;

import gov.cdc.nbs.patient.search.SearchablePatient;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SearchablePatientNameFinder {

  private static final String QUERY = """
      select
          [name].nm_use_cd,
          [name].first_nm,
          [name].middle_nm,
          [name].last_nm,
          [name].nm_prefix,
          [name].nm_suffix,
          trim(
                string_escape(
                  replace(
                    isNull([name].last_nm, '') + ' '
                  + isNull([name].first_nm, '') + ' '
                  + isNull([name].middle_nm, '') + ' '
                  + isNull([suffix].code_short_desc_txt, ''),
                    '-', ' '
                  ),
                'json'
                )
              ) as [full],
      from person_name [name]
      join NBS_SRTE..Code_value_general [use] on
                 [use].[code_set_nm] = 'P_NM_USE'
             and [use].[code] = [name].nm_use_cd
      left join NBS_SRTE..Code_value_general [suffix] on
                  [suffix].[code_set_nm] = 'P_NM_SFX'
              and [suffix].[code] = [name].nm_suffix
      where [name].person_uid = ?
      """;
  private static final int PATIENT_PARAMETER = 1;
  private static final int SUFFIX_COLUMN = 6;
  private static final int PREFIX_COLUMN = 5;
  private static final int LAST_COLUMN = 4;
  private static final int MIDDLE_COLUMN = 3;
  private static final int FIRST_COLUMN = 2;
  private static final int USE_COLUMN = 1;
  private static final int FULL_COLUMN = 7;

  private final JdbcTemplate template;
  private final RowMapper<SearchablePatient.Name> mapper;

  SearchablePatientNameFinder(final JdbcTemplate template) {
    this.template = template;
    this.mapper = new SearchablePatientNameRowMapper(
        new SearchablePatientNameRowMapper.Column(
            USE_COLUMN,
            FIRST_COLUMN,
            MIDDLE_COLUMN,
            LAST_COLUMN,
            PREFIX_COLUMN,
            SUFFIX_COLUMN,
            FULL_COLUMN));
  }

  public List<SearchablePatient.Name> find(final long patient) {
    return this.template.query(
        QUERY,
        statement -> statement.setLong(PATIENT_PARAMETER, patient),
        this.mapper);
  }
}
