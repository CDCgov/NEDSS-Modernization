package gov.cdc.nbs.patient.search.phone;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
class PatientSearchResultDetailedPhoneFinder {
  private static final String QUERY = """
      select distinct
      coalesce(
          [type].code_short_desc_txt,
          IsNull([locators].cd, '')) as [type],
        coalesce(
            [use].code_short_desc_txt,
            IsNull([locators].[use_cd],'')
        )                               as [use],
        IsNull([locators].cd, '')       as [type_cd],
        [locators].use_cd               as [use_cd],
        [phone_number].phone_nbr_txt    as [phone_number],
        [locators].as_of_date as [as_of],
      [locators].locator_uid as [locator_uid]
      from Entity_locator_participation [locators]
          join Tele_locator [phone_number] on
                  [phone_number].[tele_locator_uid] = [locators].[locator_uid]
              and [phone_number].record_status_cd   = [locators].[record_status_cd]
              and [phone_number].phone_nbr_txt is not null
          left join  NBS_SRTE..Code_value_general [type] on
                  [type].code_set_nm = 'EL_TYPE_TELE_PAT'
              and [type].code = [locators].cd
          left join NBS_SRTE..Code_value_general [use] on
                  [use].code_set_nm = 'EL_USE_TELE_PAT'
              and [use].code = [locators].[use_cd]
      where [locators].entity_uid = ?
          and [locators].[class_cd] = 'TELE'
          and [locators].record_status_cd = 'ACTIVE'
      order by
          [locators].as_of_date desc, [locators].locator_uid desc""";
  private static final int PATIENT_PARAMETER = 1;

  private final JdbcTemplate template;
  private final PatientSearchResultPhoneMapper mapper;

  PatientSearchResultDetailedPhoneFinder(final JdbcTemplate template) {
    this.template = template;
    this.mapper = new PatientSearchResultPhoneMapper();
  }

  Collection<PatientSearchResultPhone> find(final long patient) {
    return this.template.query(
        QUERY,
        statement -> statement.setLong(PATIENT_PARAMETER, patient),
        mapper);
  }
}
