package gov.cdc.nbs.patient;

import gov.cdc.nbs.entity.odse.EntityLocatorParticipationId;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

@Component
class PatientEntityLocatorHistoryRecorder {

  private static final String INSERT_SNAPSHOT = """
      insert into Entity_loc_participation_hist (
          entity_uid,
          locator_uid,
          version_ctrl_nbr,
          add_reason_cd,
          add_time,
          add_user_id,
          cd,
          cd_desc_txt,
          class_cd,
          duration_amt,
          duration_unit_cd,
          from_time,
          last_chg_reason_cd,
          last_chg_time,
          last_chg_user_id,
          locator_desc_txt,
          record_status_cd,
          record_status_time,
          status_cd,
          status_time,
          to_time,
          use_cd,
          user_affiliation_txt,
          valid_time_txt,
          as_of_date
      )
      select
          entity_uid,
          locator_uid,
          version_ctrl_nbr,
          add_reason_cd,
          add_time,
          add_user_id,
          cd,
          cd_desc_txt,
          class_cd,
          duration_amt,
          duration_unit_cd,
          from_time,
          last_chg_reason_cd,
          last_chg_time,
          last_chg_user_id,
          locator_desc_txt,
          record_status_cd,
          record_status_time,
          status_cd,
          status_time,
          to_time,
          use_cd,
          user_affiliation_txt,
          valid_time_txt,
          as_of_date
      from Entity_locator_participation
      where entity_uid = :entity
          and locator_uid = :locator
      """;


  private final JdbcClient client;

  PatientEntityLocatorHistoryRecorder(final JdbcClient client) {
    this.client = client;
  }

  void snapshot(final EntityLocatorParticipationId identifier) {
    this.client.sql(INSERT_SNAPSHOT)
        .param("entity", identifier.getEntityUid())
        .param("locator", identifier.getLocatorUid())
        .update();
  }

}
