package gov.cdc.nbs.patient.file.demographics.address;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
class PatientAddressDemographicFinder {

  private static final String QUERY = """
      select
          [locators].locator_uid          as [id],
          [locators].[as_of_date]         as [as_of],
          [locators].cd                   as [type_value],
          [type].code_short_desc_txt      as [type_name],
          [locators].use_cd               as [use_value],
          [use].[code_short_desc_txt]     as [use_name],
          [address].street_addr1          as [address_1],
          [address].street_addr2          as [address_2],
          [address].city_desc_txt         as [city],
          [address].[cnty_cd]             as [county_value],
          [county].code_desc_txt          as [county_name],
          [address].state_cd              as [state_value],
          [state].[code_desc_txt]         as [state_name],
          [address].zip_cd                as [zip_code],
          [address].[cntry_cd]            as [country_value],
          [country].code_desc_txt         as [country_name],
          [address].census_track_cd       as [census_tract],
          [locators].locator_desc_txt     as [comment]
      from Entity_locator_participation [locators]
      
          join Postal_locator [address] with (nolock) on
                  [address].[postal_locator_uid] = [locators].[locator_uid]
              and [address].record_status_cd = 'ACTIVE'
      
          join  NBS_SRTE..Code_value_general [type] with (nolock) on
                  [type].code_set_nm = 'EL_TYPE_PST_PAT'
              and [type].code = [locators].cd
      
          join NBS_SRTE..Code_value_general [use] with (nolock) on
                  [use].code_set_nm = 'EL_USE_PST_PAT'
              and [use].code = [locators].[use_cd]
      
          left join NBS_SRTE..State_county_code_value [county] with (nolock) on
                  [county].[code] = [address].[cnty_cd]
      
          left join NBS_SRTE..State_code [state] with (nolock) on
                  [state].state_cd = [address].state_cd
      
          left join NBS_SRTE..Country_code [country] with (nolock) on
                  [country].code = [address].cntry_cd
      
      
      where   [locators].entity_uid = ?
          and [locators].[class_cd] = 'PST'
          and [locators].record_status_cd = 'ACTIVE'
          and [locators].use_cd not in ('BIR', 'DTH')
      """;

  private final JdbcClient client;
  private final RowMapper<PatientAddressDemographic> mapper;

  PatientAddressDemographicFinder(final JdbcClient client) {
    this.client = client;
    this.mapper = new PatientAddressDemographicRowMapper();
  }

  List<PatientAddressDemographic> find(final long patient) {
    return this.client.sql(QUERY)
        .param(patient)
        .query(this.mapper)
        .list();
  }
}
