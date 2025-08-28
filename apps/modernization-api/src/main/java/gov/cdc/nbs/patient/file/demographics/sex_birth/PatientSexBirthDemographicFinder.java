package gov.cdc.nbs.patient.file.demographics.sex_birth;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
class PatientSexBirthDemographicFinder {

  private static final String QUERY = """
      select
          [patient].as_of_date_sex                as [as_of],
          [patient].[birth_time]                  as [born_on],
          [patient].deceased_time                 as [deceased_on],
          [patient].birth_gender_cd               as [birth_gender_value],
          [birth_gender].[code_short_desc_txt]    as [birth_gender_name],
          [patient].multiple_birth_ind            as [multiple_birth_value],
          [multiple_birth].code_short_desc_txt    as [multiple_birth_name],
          [patient].birth_order_nbr               as [order],
          [address].city_desc_txt                 as [city],
          [address].state_cd                      as [state_value],
          [state].[code_desc_txt]                 as [state_name],
          [county].code                           as [county_value],
          [county].code_desc_txt                  as [county_name],
          [address].[cntry_cd]                    as [country_value],
          [country].code_desc_txt                 as [country_name],
          --
          [patient].curr_sex_cd                   as [current_gender_value],
          [current_gender].[code_short_desc_txt]  as [current_gender_name],
          [unknown_reason].[code]                 as [unknown_reason_value],
          [unknown_reason].code_short_desc_txt    as [unknown_reason_name],
          [patient].[preferred_gender_cd]         as [transgender_value],
          [transgender].[code_short_desc_txt]     as [transgender_name],
          [patient].[additional_gender_cd]        as [addition_gender]
      from Person [patient]
      
          left join NBS_SRTE..Code_value_general [multiple_birth] on
                  [multiple_birth].code_set_nm = 'YNU'
              and [multiple_birth].[code] = [patient].multiple_birth_ind
      
          left join Entity_locator_participation [locators] on
                  [locators].entity_uid = [patient].person_uid
              and [locators].[use_cd] = 'BIR'
              and [locators].[class_cd] = 'PST'
              and [locators].record_status_cd = 'ACTIVE'
      
          left join Postal_locator [address] on
                  [address].[postal_locator_uid] = [locators].[locator_uid]
      
          left join NBS_SRTE..State_code [state] on
                  [state].state_cd = [address].state_cd
      
          left join NBS_SRTE..State_county_code_value [county]  with (nolock) on
                  [county].[code] = [address].[cnty_cd]
      
          left join NBS_SRTE..Country_code [country] on
                  [country].code = [address].cntry_cd
          --
          left join NBS_SRTE..Code_value_general [current_gender] with (nolock) on
                  [current_gender].code_set_nm = 'SEX'
              and [current_gender].[code] = [patient].[curr_sex_cd]
      
          left join NBS_SRTE..Code_value_general [birth_gender] with (nolock) on
                  [birth_gender].code_set_nm = 'SEX'
              and [birth_gender].[code] = [patient].[birth_gender_cd]
      
          left join NBS_SRTE..Code_value_general [unknown_reason] with (nolock) on
                  [unknown_reason].code_set_nm = 'SEX_UNK_REASON'
              and [unknown_reason].[code] = [patient].[sex_unk_reason_cd]
              and [current_gender].code = 'U'
      
          left join NBS_SRTE..Code_value_general [transgender] with (nolock) on
                  [transgender].[code_set_nm] = 'NBS_STD_GENDER_PARPT'
              and [transgender].[code] = [patient].[preferred_gender_cd]
      
      where [patient].person_uid = ?
          and [patient].cd = 'PAT'
          and [patient].as_of_date_sex is not null
      """;

  private final JdbcClient client;
  private final RowMapper<PatientSexBirthDemographic> mapper;

  PatientSexBirthDemographicFinder(final JdbcClient client) {
    this.client = client;
    this.mapper = new PatientSexBirthDemographicRowMapper();
  }

  Optional<PatientSexBirthDemographic> find(final long patient) {
    return this.client.sql(QUERY)
        .param(patient)
        .query(this.mapper)
        .optional();
  }
}
