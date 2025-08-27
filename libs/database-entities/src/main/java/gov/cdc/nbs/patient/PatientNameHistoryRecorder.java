package gov.cdc.nbs.patient;

import gov.cdc.nbs.entity.odse.PersonNameId;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

@Component
class PatientNameHistoryRecorder {

  private final JdbcClient client;

  PatientNameHistoryRecorder(final JdbcClient client) {
    this.client = client;
  }

  private static final String INSERT_SNAPSHOT = """
      insert into Person_name_hist (
          person_uid,
          person_name_seq,
          version_ctrl_nbr,
          add_reason_cd,
          add_time,
          add_user_id,
          default_nm_ind,
          duration_amt,
          duration_unit_cd,
          first_nm,
          first_nm_sndx,
          from_time,
          last_chg_reason_cd,
          last_chg_time,
          last_chg_user_id,
          last_nm,
          last_nm_sndx,
          last_nm2,
          last_nm2_sndx,
          middle_nm,
          middle_nm2,
          nm_degree,
          nm_prefix,
          nm_suffix,
          nm_use_cd,
          record_status_cd,
          record_status_time,
          status_cd,
          status_time,
          to_time,
          user_affiliation_txt,
          as_of_date
      )
      select
          person_uid,
          person_name_seq,
          (select count(*) + 1 from Person_name_hist where person_uid = :patient and person_name_seq = :sequence),
          add_reason_cd,
          add_time,
          add_user_id,
          default_nm_ind,
          duration_amt,
          duration_unit_cd,
          first_nm,
          first_nm_sndx,
          from_time,
          last_chg_reason_cd,
          last_chg_time,
          last_chg_user_id,
          last_nm,
          last_nm_sndx,
          last_nm2,
          last_nm2_sndx,
          middle_nm,
          middle_nm2,
          nm_degree,
          nm_prefix,
          nm_suffix,
          nm_use_cd,
          record_status_cd,
          record_status_time,
          status_cd,
          status_time,
          to_time,
          user_affiliation_txt,
          as_of_date
      from Person_name
      where person_uid = :patient
        and person_name_seq = :sequence
      """;

  void snapshot(final PersonNameId identifier) {
    this.client.sql(INSERT_SNAPSHOT)
        .param("patient", identifier.getPersonUid())
        .param("sequence", identifier.getPersonNameSeq())
        .update();
  }
}
