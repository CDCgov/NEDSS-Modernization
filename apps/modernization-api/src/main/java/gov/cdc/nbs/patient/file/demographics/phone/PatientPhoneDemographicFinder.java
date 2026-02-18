package gov.cdc.nbs.patient.file.demographics.phone;

import java.util.List;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

@Component
class PatientPhoneDemographicFinder {

  private static final String QUERY =
      """
      select
          [locators].locator_uid          as [id],
          [locators].[as_of_date]         as [as_of],
          [locators].cd                   as [type_value],
          [type].code_short_desc_txt      as [type_name],
          [locators].use_cd               as [use_value],
          [use].[code_short_desc_txt]     as [use_name],
          [phone_number].cntry_cd         as [country_code],
          [phone_number].phone_nbr_txt    as [phone_number],
          [phone_number].extension_txt    as [extension],
          [phone_number].email_address    as [email_address],
          [phone_number].[url_address]    as [url],
          [locators].locator_desc_txt     as [comment]
      from Entity_locator_participation [locators]

          join Tele_locator [phone_number] on
                  [phone_number].[tele_locator_uid] = [locators].[locator_uid]
              and [phone_number].record_status_cd = 'ACTIVE'

          join NBS_SRTE..Code_value_general [type] on
                  [type].code_set_nm = 'EL_TYPE_TELE_PAT'
              and [type].code = [locators].cd

          join NBS_SRTE..Code_value_general [use] on
                  [use].code_set_nm = 'EL_USE_TELE_PAT'
              and [use].code = [locators].[use_cd]

      where   [locators].entity_uid = ?
          and [locators].[class_cd] = 'TELE'
          and [locators].record_status_cd = 'ACTIVE'

      order by
          [locators].[as_of_date] desc,
          [locators].locator_uid desc
      """;

  private final JdbcClient client;
  private final RowMapper<PatientPhoneDemographic> mapper;

  PatientPhoneDemographicFinder(final JdbcClient client) {
    this.client = client;
    this.mapper = new PatientPhoneDemographicRowMapper();
  }

  List<PatientPhoneDemographic> find(final long patient) {
    return this.client.sql(QUERY).param(patient).query(this.mapper).list();
  }
}
