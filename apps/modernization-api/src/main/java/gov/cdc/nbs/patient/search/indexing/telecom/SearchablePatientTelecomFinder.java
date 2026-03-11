package gov.cdc.nbs.patient.search.indexing.telecom;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class SearchablePatientTelecomFinder {

  private static final String QUERY =
      """
      select distinct
          [locator].email_address         as [email],
          [locator].[phone_nbr_txt]       as [phone_number],
          [locator].extension_txt         as [extension],
          [participation].cd              as [type_cd],
          [participation].use_cd          as [use_cd],
          [participation].[as_of_date],
          [participation].[locator_uid]

      from Entity_locator_participation [participation]

          join Tele_locator [locator] on
                  [locator].[tele_locator_uid] = [participation].[locator_uid]

      where   [participation].entity_uid = ?
          and [participation].[class_cd] = 'TELE'
      order by
          [participation].[as_of_date] desc,
          [participation].[locator_uid] desc
      """;
  private static final int ADDRESS_COLUMN = 1;
  private static final int NUMBER_COLUMN = 2;
  private static final int EXTENSION_COLUMN = 3;
  private static final int TYPE_COLUMN = 4;
  private static final int USE_COLUMN = 5;

  private static final int PATIENT_PARAMETER = 1;

  private final JdbcTemplate template;
  private final SearchablePatientPhoneMapper.Column phoneColumns;
  private final SearchablePatientEmailMapper.Column emailColumns;

  public SearchablePatientTelecomFinder(final JdbcTemplate template) {
    this.template = template;
    this.emailColumns = new SearchablePatientEmailMapper.Column(ADDRESS_COLUMN);
    this.phoneColumns =
        new SearchablePatientPhoneMapper.Column(
            NUMBER_COLUMN, EXTENSION_COLUMN, TYPE_COLUMN, USE_COLUMN);
  }

  public SearchablePatientTelecom find(final long patient) {

    SearchablePatientTelecomRowCallbackHandler handler =
        new SearchablePatientTelecomRowCallbackHandler(this.emailColumns, this.phoneColumns);

    this.template.query(QUERY, statement -> statement.setLong(PATIENT_PARAMETER, patient), handler);

    return handler.telecom();
  }
}
