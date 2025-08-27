package gov.cdc.nbs.patient;

import gov.cdc.nbs.entity.odse.EntityLocatorParticipationId;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

@Component
class PatientPostalLocatorHistoryRecorder {

  private static final String INSERT_SNAPSHOT = """
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
              (select count(*) FROM Postal_locator_hist WHERE postal_locator_uid = :locator),
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
          where postal_locator_uid = :locator
      """;

  private final JdbcClient client;

  PatientPostalLocatorHistoryRecorder(final JdbcClient client) {
    this.client = client;
  }

  void record(final EntityLocatorParticipationId identifier) {
    this.client.sql(INSERT_SNAPSHOT)
        .param("locator", identifier.getLocatorUid())
        .update();
  }
}
