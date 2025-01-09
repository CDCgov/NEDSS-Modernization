package gov.cdc.nbs.patient.profile.summary.address;

import gov.cdc.nbs.address.Address;
import gov.cdc.nbs.address.AddressRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
class PatientSummaryAddressFinder {
  private static final String QUERY = """
      select
          coalesce(
                [type].code_short_desc_txt,
                IsNull([locators].cd, '')
          )                               as [type],
          coalesce(
                [use].code_short_desc_txt,
                IsNull([locators].[use_cd],'')
          )                               as [use],
          [address].street_addr1          as [address_1],
          [address].street_addr2          as [address_2],
          [address].city_desc_txt         as [city],
          [state].[state_nm]              as [state],
          [address].zip_cd                as [zipcode],
          [country].code_short_desc_txt   as [country],
          [county].code_desc_txt          as [county]
      from Entity_locator_participation [locators]

          join Postal_locator [address] on
                  [address].[postal_locator_uid] = [locators].[locator_uid]
              and [address].record_status_cd = [locators].record_status_cd

          left join  NBS_SRTE..Code_value_general [type] on
                  [type].code_set_nm = 'EL_TYPE_PST_PAT'
              and [type].code = [locators].cd

          left join NBS_SRTE..Code_value_general [use] on
                  [use].code_set_nm = 'EL_USE_PST_PAT'
              and [use].code = [locators].[use_cd]

          left join NBS_SRTE..State_code [state] on
                  [state].state_cd = [address].state_cd

          left join NBS_SRTE..Country_code [country] on
                  [country].code = [address].cntry_cd

          left join NBS_SRTE..State_county_code_value [county] on [county].code = [address].cnty_cd

      where   [locators].entity_uid = ?
          and [locators].[class_cd] = 'PST'
          and ([locators].[use_cd] IS NULL OR [locators].[use_cd] not in ('BIR', 'DTH', 'H'))
          and [locators].[record_status_cd] = 'ACTIVE'
      """;
  private static final int PATIENT_PARAMETER = 1;

  private final JdbcTemplate template;
  private final RowMapper<Address> mapper;

  PatientSummaryAddressFinder(final JdbcTemplate template) {
    this.template = template;
    this.mapper = new AddressRowMapper(new AddressRowMapper.Columns());
  }

  Collection<Address> find(final long patient) {
    return this.template.query(
        QUERY,
        statement -> statement.setLong(PATIENT_PARAMETER, patient),
        this.mapper);
  }

}
