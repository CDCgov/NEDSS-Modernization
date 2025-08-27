package gov.cdc.nbs.patient;

import gov.cdc.nbs.entity.odse.PersonEthnicGroupId;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

@Component
class PatientEthnicityHistoryRecorder {

  private static final String INSERT_SNAPSHOT = """
          insert into Person_ethnic_group_hist (
              person_uid,
              ethnic_group_cd,
              version_ctrl_nbr,
              add_reason_cd,
              add_time,
              add_user_id,
              ethnic_group_desc_txt,
              last_chg_reason_cd,
              last_chg_time,
              last_chg_user_id,
              record_status_cd,
              record_status_time,
              user_affiliation_txt
          )
          select
              person_uid,
              ethnic_group_cd,
              (select count(*) + 1 from Person_ethnic_group_hist where person_uid = :patient and ethnic_group_cd = :group),
              add_reason_cd,
              add_time,
              add_user_id,
              ethnic_group_desc_txt,
              last_chg_reason_cd,
              last_chg_time,
              last_chg_user_id,
              record_status_cd,
              record_status_time,
              user_affiliation_txt
          from Person_ethnic_group
          where person_uid = :patient
          and ethnic_group_cd = :group
      """;

  private final JdbcClient client;

  PatientEthnicityHistoryRecorder(final JdbcClient client) {
    this.client = client;
  }

  public void snapshot(final PersonEthnicGroupId identifier) {
    this.client.sql(INSERT_SNAPSHOT)
        .param("patient", identifier.getPersonUid())
        .param("group", identifier.getEthnicGroupCd())
        .update();
  }
}
