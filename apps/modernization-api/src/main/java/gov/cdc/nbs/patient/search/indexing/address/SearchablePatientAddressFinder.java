package gov.cdc.nbs.patient.search.indexing.address;

import gov.cdc.nbs.patient.search.SearchablePatient;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SearchablePatientAddressFinder {

  private static final String QUERY = """
      select
          [address].street_addr1          as [address_1],
          [address].street_addr2          as [address_2],
          [address].city_desc_txt         as [city],
          [address].state_cd              as [state],
          [address].zip_cd                as [zip],
          [address].cnty_cd               as [county],
          [address].cntry_cd              as [country]
      from Entity_locator_participation [locators]
            
          join Postal_locator [address] on
                  [address].[postal_locator_uid] = [locators].[locator_uid]
            
      where   [locators].entity_uid = ?
          and [locators].[class_cd] = 'PST'
          and [locators].use_cd not in ('BIR', 'DTH')
      """;
  private static final int PATIENT_PARAMETER = 1;
  private static final int ADDRESS_1_COLUMN = 1;
  private static final int ADDRESS_2_COLUMN = 2;
  private static final int CITY_COLUMN = 3;
  private static final int STATE_COLUMN = 4;
  private static final int ZIP_COLUMN = 5;
  private static final int COUNTY_COLUMN = 6;
  private static final int COUNTRY_COLUMN = 7;

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
           COUNTRY_COLUMN
       )
    );
  }

  public List<SearchablePatient.Address> find(final long patient) {
    return this.template.query(
        QUERY,
        statement -> statement.setLong(PATIENT_PARAMETER, patient),
        this.mapper
    );
  }
}
