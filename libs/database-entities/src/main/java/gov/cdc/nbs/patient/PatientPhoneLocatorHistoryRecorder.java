package gov.cdc.nbs.patient;

import gov.cdc.nbs.entity.odse.EntityLocatorParticipationId;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

@Component
class PatientPhoneLocatorHistoryRecorder {

  private static final String INSERT_SNAPSHOT = """
          insert into Tele_locator_hist (
              tele_locator_uid,
              version_ctrl_nbr,
              add_reason_cd,
              add_time,
              add_user_id,
              cntry_cd,
              email_address,
              extension_txt,
              last_chg_reason_cd,
              last_chg_time,
              last_chg_user_id,
              phone_nbr_txt,
              record_status_cd,
              record_status_time,
              url_address,
              user_affiliation_txt
          )
          select
              tele_locator_uid,
              (select count(*) FROM Tele_locator_hist WHERE tele_locator_uid = :locator),
              add_reason_cd,
              add_time,
              add_user_id,
              cntry_cd,
              email_address,
              extension_txt,
              last_chg_reason_cd,
              last_chg_time,
              last_chg_user_id,
              phone_nbr_txt,
              record_status_cd,
              record_status_time,
              url_address,
              user_affiliation_txt
          from Tele_locator
          where tele_locator_uid = :locator
      """;

  private final JdbcClient client;

  PatientPhoneLocatorHistoryRecorder(final JdbcClient client) {
    this.client = client;
  }

  void snapshot(final EntityLocatorParticipationId identifier) {
    this.client.sql(INSERT_SNAPSHOT)
        .param("locator", identifier.getLocatorUid())
        .update();
  }
}
