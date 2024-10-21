package gov.cdc.nbs.patient.search.phone;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import gov.cdc.nbs.patient.search.SearchablePatient;
import java.util.Collection;

@Component
class PatientSearchResultDetailedPhoneFinder {

  private static final String QUERY =
      """
              select distinct
                  [phone_number].phone_nbr_txt    as [phone_number],
                  [phone_number].extension_txt    as [extension],
                  [locators].cd                   as [type_cd],
                  [locators].use_cd               as [use_cd]
              from Entity_locator_participation [locators]

                  join Tele_locator [phone_number] on
                          [phone_number].[tele_locator_uid] = [locators].[locator_uid]
                      and [phone_number].record_status_cd   = [locators].[record_status_cd]
                      and [phone_number].phone_nbr_txt is not null


              where   [locators].entity_uid = ?
                  and [locators].[class_cd] = 'TELE'
                  and [locators].cd <> 'NET'
                  and [locators].record_status_cd = 'ACTIVE'
          """;
  private static final int PATIENT_PARAMETER = 1;
  private static final int PHONE_COLUMN = 1;
  private static final int EXTENSION_COLUMN = 2;
  private static final int TYPE_COLUMN = 3;
  private static final int USE_COLUMN = 4;

  private final JdbcTemplate template;

  PatientSearchResultDetailedPhoneFinder(final JdbcTemplate template) {
    this.template = template;
  }

  Collection<SearchablePatient.Phone> find(final long patient) {
    return this.template.query(
        QUERY,
        statement -> statement.setLong(PATIENT_PARAMETER, patient),
        (rs, row) -> new SearchablePatient.Phone(rs.getString(PHONE_COLUMN), rs.getString(EXTENSION_COLUMN),
            rs.getString(TYPE_COLUMN), rs.getString(USE_COLUMN)));
  }
}
