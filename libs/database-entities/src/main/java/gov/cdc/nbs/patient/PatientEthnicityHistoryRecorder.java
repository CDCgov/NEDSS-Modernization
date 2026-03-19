package gov.cdc.nbs.patient;

import gov.cdc.nbs.entity.odse.PersonEthnicGroupId;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

@Component
class PatientEthnicityHistoryRecorder {

  private static final String INSERT_SNAPSHOT =
      """
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
              [ethnicity].person_uid,
              [ethnicity].ethnic_group_cd,
              [patient].version_ctrl_nbr,
              [ethnicity].add_reason_cd,
              [ethnicity].add_time,
              [ethnicity].add_user_id,
              [ethnicity].ethnic_group_desc_txt,
              [ethnicity].last_chg_reason_cd,
              [ethnicity].last_chg_time,
              [ethnicity].last_chg_user_id,
              [ethnicity].record_status_cd,
              [ethnicity].record_status_time,
              [ethnicity].user_affiliation_txt
          from Person_ethnic_group [ethnicity]

              join [Person] [patient] on
                  [patient].person_uid = [ethnicity].person_uid

          where [ethnicity].person_uid = :patient
          and [ethnicity].ethnic_group_cd = :group
      """;

  private final JdbcClient client;

  PatientEthnicityHistoryRecorder(final JdbcClient client) {
    this.client = client;
  }

  public void snapshot(final PersonEthnicGroupId identifier) {
    this.client
        .sql(INSERT_SNAPSHOT)
        .param("patient", identifier.getPersonUid())
        .param("group", identifier.getEthnicGroupCd())
        .update();
  }
}
