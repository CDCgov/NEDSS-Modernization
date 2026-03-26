package gov.cdc.nbs.patient.file.demographics.mortality;

import java.util.Optional;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

@Component
class PatientMortalityDemographicFinder {

  private static final String QUERY =
      """
      select
          [patient].as_of_date_morbidity          as [as_of],
          [patient].deceased_ind_cd               as [deceased_value],
          [deceased].code_short_desc_txt          as [deceased_name],
          [patient].deceased_time                 as [deceased_on],
          [address].city_desc_txt                 as [city],
          [address].state_cd                      as [state_value],
          [state].[code_desc_txt]                 as [state_name],
          [county].code                           as [county_value],
          [county].code_desc_txt                  as [county_name],
          [address].[cntry_cd]                    as [country_value],
          [country].code_desc_txt                 as [country_name]
      from Person [patient]

          left join NBS_SRTE..Code_value_general [deceased] on
                  [deceased].code_set_nm = 'YNU'
              and [deceased].[code] = [patient].deceased_ind_cd

          left join Entity_locator_participation [locators] on
                  [locators].entity_uid = [patient].person_uid
              and [locators].[use_cd] = 'DTH'
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

      where [patient].person_uid = ?
          and [patient].cd = 'PAT'
          and [patient].[as_of_date_morbidity] is not null
      """;

  private final JdbcClient client;
  private final RowMapper<PatientMortalityDemographic> mapper;

  PatientMortalityDemographicFinder(final JdbcClient client) {
    this.client = client;
    this.mapper = new PatientMortalityDemographicRowMapper();
  }

  Optional<PatientMortalityDemographic> find(final long patient) {
    return this.client.sql(QUERY).param(patient).query(this.mapper).optional();
  }
}
