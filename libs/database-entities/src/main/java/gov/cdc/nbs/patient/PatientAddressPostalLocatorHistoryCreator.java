package gov.cdc.nbs.patient;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class PatientAddressPostalLocatorHistoryCreator {
    private static final int VERSION_PARAMETER = 1;
    private static final int LOCATOR_PARAMETER = 2;
    private final JdbcTemplate template;

    private static final String CREATE_POSTAL_LOCATOR_HIST = """
        insert into Postal_locator_hist (
            postal_locator_uid,
            version_ctrl_nbr,
            add_reason_cd,
            add_time,
            add_user_id,
            census_block_cd,
            census_minor_civil_division_cd,
            census_track_cd,
            city_cd,
            city_desc_txt,
            cntry_cd,
            cntry_desc_txt,
            cnty_cd,
            cnty_desc_txt,
            last_chg_reason_cd,
            last_chg_time,
            last_chg_user_id,
            MSA_congress_district_cd,
            record_status_cd,
            record_status_time,
            region_district_cd,
            state_cd,
            street_addr1,
            street_addr2,
            user_affiliation_txt,
            zip_cd,
            geocode_match_ind,
            within_city_limits_ind,
            census_tract
        )
        select 
            postal_locator_uid,
            ?,
            add_reason_cd,
            add_time,
            add_user_id,
            census_block_cd,
            census_minor_civil_division_cd,
            census_track_cd,
            city_cd,
            city_desc_txt,
            cntry_cd,
            cntry_desc_txt,
            cnty_cd,
            cnty_desc_txt,
            last_chg_reason_cd,
            last_chg_time,
            last_chg_user_id,
            MSA_congress_district_cd,
            record_status_cd,
            record_status_time,
            region_district_cd,
            state_cd,
            street_addr1,
            street_addr2,
            user_affiliation_txt,
            zip_cd,
            geocode_match_ind,
            within_city_limits_ind,
            census_tract
        from Postal_locator
        where postal_locator_uid = ?
    """;

    public PatientAddressPostalLocatorHistoryCreator(JdbcTemplate template) {
        this.template = template;
    }

    public void createPostalLocatorHistory(long locatorId, int version) {
        this.template.update(
                CREATE_POSTAL_LOCATOR_HIST,
                statement -> {
                    statement.setInt(VERSION_PARAMETER, version);
                    statement.setLong(LOCATOR_PARAMETER, locatorId);
                }
        );
    }
}
