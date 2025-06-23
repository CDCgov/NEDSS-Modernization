package gov.cdc.nbs.patient.file.demographics.general;

import gov.cdc.nbs.data.sensitive.SensitiveValueResolver;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
class PatientGeneralInformationDemographicFinder {

  private static final String QUERY = """
        select
      [patient].as_of_date_general            as [as_of],
      [patient].[marital_status_cd]           as [marital_status_value],
      [marital_status].code_short_desc_txt    as [marital_status_name],
      [patient].mothers_maiden_nm             as [mother_madien_name],
      [patient].adults_in_house_nbr           as [adults_in_residence],
      [patient].children_in_house_nbr         as [children_in_residence],
      [patient].[occupation_cd]               as [occupation_value],
      [occupation].code_short_desc_txt        as [occupation_name],
      [patient].[education_level_cd]          as [education_value],
      [education].code_short_desc_txt         as [education_name],
      [patient].[prim_lang_cd]                as [primary_language_value],
      [language].[code_short_desc_txt]        as [primay_language_name],
      [patient].[speaks_english_cd]           as [speaks_english_value],
      [speaks_english].code_short_desc_txt    as [speaks_english_name],
      [patient].[ehars_id]                    as [state_hiv_case]
      from Person [patient]
      
        left join NBS_SRTE..Code_value_general [marital_status] with (nolock) on
                [marital_status].code_set_nm = 'P_MARITAL'
          and   [marital_status].code = [patient].[marital_status_cd]
      
      left join
            NBS_SRTE..NAICS_Industry_code [occupation] with (nolock) on
            [occupation].code = [patient].[occupation_cd]
      
      left join NBS_SRTE..Code_value_general [education] with (nolock) on
              [education].code_set_nm = 'P_EDUC_LVL'
          and [education].code = [patient].[education_level_cd]
      
      left join NBS_SRTE..Language_code [language] with (nolock) on
              [language].code = [patient].prim_lang_cd
      
      left join NBS_SRTE..Code_value_general [speaks_english] with (nolock) on
              [speaks_english].code_set_nm = 'YNU'
          and [speaks_english].[code] = [patient].speaks_english_cd  \s
      
        where [patient].person_uid = ?
            and [patient].cd = 'PAT'
            and [patient].as_of_date_general is not null
      """;

  private final JdbcClient client;
  private final RowMapper<PatientGeneralInformationDemographic> mapper;

  PatientGeneralInformationDemographicFinder(
      final JdbcClient client,
      final SensitiveValueResolver resolver
  ) {
    this.client = client;
    this.mapper = new PatientGeneralInformationDemographicRowMapper(resolver);
  }

  Optional<PatientGeneralInformationDemographic> find(final long patient) {
    return this.client.sql(QUERY)
        .param(patient)
        .query(this.mapper)
        .optional();
  }
}
