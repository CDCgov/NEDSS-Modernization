package gov.cdc.nbs.patient.file.demographics.name;

import java.util.List;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

@Component
class PatientNameDemographicFinder {
  private static final String QUERY =
      """
      select
            [name].[person_name_seq]        as [sequence],
            [name].as_of_date               as [as_of],
            [name].nm_use_cd                as [type_value],
            [use].[code_short_desc_txt]     as [type_name],
            [name].nm_prefix                as [prefix_value],
            [prefix].code_short_desc_txt    as [prefix_name],
            [name].first_nm                 as [first],
            [name].middle_nm                as [middle],
            [name].middle_nm2               as [second_middle],
            [name].last_nm                  as [last],
            [name].last_nm2                 as [second_last],
            [name].nm_suffix                as [suffix_value],
            [suffix].[code_short_desc_txt]  as [suffix_name],
            [name].nm_degree                as [degree_value],
            [degree].[code_short_desc_txt]  as [degree_name]
      from Person_name [name]

            join NBS_SRTE..Code_value_general [use] with (nolock) on
                    [use].[code_set_nm] = 'P_NM_USE'
                and [use].[code] = [name].nm_use_cd

            left join NBS_SRTE..Code_value_general [prefix] with (nolock) on
                    [prefix].[code_set_nm] = 'P_NM_PFX'
                and [prefix].[code] = [name].nm_prefix

            left join NBS_SRTE..Code_value_general [suffix] with (nolock) on
                    [suffix].[code_set_nm] = 'P_NM_SFX'
                and [suffix].[code] = [name].nm_suffix

            left join NBS_SRTE..Code_value_general [degree] with (nolock) on
                    [degree].[code_set_nm] = 'P_NM_DEG'
                and [degree].[code] = [name].nm_degree

      where   [name].record_status_cd = 'ACTIVE'
            and [name].person_uid = ?
       order by
            [name].as_of_date desc,
            [name].[person_name_seq] desc
      """;

  private final JdbcClient client;
  private final PatientNameDemographicRowMapper mapper;

  PatientNameDemographicFinder(final JdbcClient client) {
    this.client = client;
    this.mapper = new PatientNameDemographicRowMapper();
  }

  List<PatientNameDemographic> find(final long patient) {
    return this.client.sql(QUERY).param(patient).query(this.mapper).list();
  }
}
