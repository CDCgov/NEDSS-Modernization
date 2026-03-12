package gov.cdc.nbs.patient;

import gov.cdc.nbs.entity.odse.PatientRaceId;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

@Component
class PatientRaceHistoryRecorder {

  private static final String INSERT_SNAPSHOT =
      """
      insert into Person_race_hist (
          person_uid,
          race_cd,
          version_ctrl_nbr,
          add_reason_cd,
          add_time,
          add_user_id,
          last_chg_reason_cd,
          last_chg_time,
          last_chg_user_id,
          race_category_cd,
          race_desc_txt,
          record_status_cd,
          record_status_time,
          user_affiliation_txt,
          as_of_date
      )
      select
          [race].person_uid,
          [race].race_cd,
          [patient].version_ctrl_nbr,
          [race].add_reason_cd,
          [race].add_time,
          [race].add_user_id,
          [race].last_chg_reason_cd,
          [race].last_chg_time,
          [race].last_chg_user_id,
          [race].race_category_cd,
          [race].race_desc_txt,
          [race].record_status_cd,
          [race].record_status_time,
          [race].user_affiliation_txt,
          [race].as_of_date
      from Person_race [race]

          join [Person] [patient] on
              [patient].person_uid = [race].person_uid

      where [race].person_uid = :patient
        and [race].race_cd = :race
      """;

  private final JdbcClient client;

  PatientRaceHistoryRecorder(final JdbcClient client) {
    this.client = client;
  }

  void snapshot(final PatientRaceId identifier) {
    this.client
        .sql(INSERT_SNAPSHOT)
        .param("patient", identifier.getPatient())
        .param("race", identifier.getRace())
        .update();
  }
}
