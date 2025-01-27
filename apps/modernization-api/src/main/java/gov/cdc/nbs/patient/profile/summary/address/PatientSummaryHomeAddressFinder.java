package gov.cdc.nbs.patient.profile.summary.address;

import gov.cdc.nbs.address.Address;
import gov.cdc.nbs.address.AddressRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Optional;

@Component
class PatientSummaryHomeAddressFinder {
  private static final String QUERY = """
    select
          coalesce(
                [type].code_short_desc_txt,
                [locators].cd
          )                               as [type],
          coalesce(
                [use].code_short_desc_txt,
                [locators].[use_cd]
          )                               as [use],
          [address].street_addr1          as [address_1],
          [address].street_addr2          as [address_2],
          [address].city_desc_txt         as [city],
          [state].[state_nm]              as [state],
          [address].zip_cd                as [zip],
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
        and [locators].use_cd = 'H'
        and [locators].record_status_cd = 'ACTIVE'
        and [locators].as_of_date = (
                  select
                      max(eff_home.as_of_date)
                  from Entity_locator_participation [eff_home]
    
                  where   [eff_home].[entity_uid]         = [locators].entity_uid
                      and [eff_home].[class_cd]           = [locators].[class_cd]
                      and [eff_home].[use_cd]             = [locators].[use_cd]
                      and [eff_home].[record_status_cd]   = [locators].[record_status_cd]
                      and [eff_home].[as_of_date]         <= ?
              )
    """;
  private static final int PATIENT_PARAMETER = 1;
  private static final int AS_OF_PARAMETER = 2;

  private final JdbcTemplate template;
  private final RowMapper<Address> mapper;

  PatientSummaryHomeAddressFinder(final JdbcTemplate template) {
    this.template = template;
    this.mapper = new AddressRowMapper(new AddressRowMapper.Columns());
  }

  Optional<Address> find(final long patient, final LocalDate asOf) {
    return this.template.query(
        QUERY,
        statement -> {
          statement.setLong(PATIENT_PARAMETER, patient);
          statement.setTimestamp(AS_OF_PARAMETER, Timestamp.valueOf(asOf.atStartOfDay()));
        },
        this.mapper).stream()
        .findFirst();
  }

}
