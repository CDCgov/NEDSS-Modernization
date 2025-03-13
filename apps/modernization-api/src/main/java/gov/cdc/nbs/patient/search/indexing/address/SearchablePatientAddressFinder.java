package gov.cdc.nbs.patient.search.indexing.address;

import gov.cdc.nbs.patient.search.SearchablePatient;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SearchablePatientAddressFinder {

  private static final String QUERY =
      """
          select
              [address].street_addr1          as [address_1],
              [address].street_addr2          as [address_2],
              [address].city_desc_txt         as [city],
              [address].state_cd              as [state],
              [address].zip_cd                as [zip],
              [address].cnty_cd               as [county],
              [address].cntry_cd              as [country],
              [srte_county].code_desc_txt     as [county_text],
              [srte_state].state_nm           as [state_text],
              [srte_country].code_short_desc_txt as [country_text]
          from Entity_locator_participation [locators]

              join Postal_locator [address] on
                      [address].[postal_locator_uid] = [locators].[locator_uid]
              left join NBS_SRTE..State_county_code_value [srte_county] on [srte_county].code = [address].cnty_cd
              left join NBS_SRTE..State_code [srte_state] on [srte_state].state_cd = [address].state_cd
              left join NBS_SRTE..Country_code [srte_country] on [srte_country].code = [address].cntry_cd
          where   [locators].entity_uid = ?
              and [locators].[class_cd] = 'PST'
              and ([locators].use_cd IS NULL OR [locators].use_cd not in ('BIR', 'DTH'))
          order by
              [locators].as_of_date  desc,
              [locators].locator_uid desc
          """;
  private static final int PATIENT_PARAMETER = 1;
  private static final int ADDRESS_1_COLUMN = 1;
  private static final int ADDRESS_2_COLUMN = 2;
  private static final int CITY_COLUMN = 3;
  private static final int STATE_COLUMN = 4;
  private static final int ZIP_COLUMN = 5;
  private static final int COUNTY_COLUMN = 6;
  private static final int COUNTRY_COLUMN = 7;
  private static final int COUNTY_TEXT_COLUMN = 8;
  private static final int STATE_TEXT_COLUMN = 9;
  private static final int COUNTRY_TEXT_COLUMN = 10;

  private final JdbcTemplate template;
  private final RowMapper<SearchablePatient.Address> mapper;

  public SearchablePatientAddressFinder(final JdbcTemplate template) {
    this.template = template;
    this.mapper = new SearchablePatientAddressRowMapper(
        new SearchablePatientAddressRowMapper.Column(
            ADDRESS_1_COLUMN,
            ADDRESS_2_COLUMN,
            CITY_COLUMN,
            STATE_COLUMN,
            ZIP_COLUMN,
            COUNTY_COLUMN,
            COUNTRY_COLUMN,
            COUNTY_TEXT_COLUMN,
            STATE_TEXT_COLUMN,
            COUNTRY_TEXT_COLUMN));
  }

  public List<SearchablePatient.Address> find(final long patient) {
    return this.template.query(
        QUERY,
        statement -> statement.setLong(PATIENT_PARAMETER, patient),
        this.mapper);
  }
}
