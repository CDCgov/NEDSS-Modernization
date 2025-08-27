package gov.cdc.nbs.patient;

import gov.cdc.nbs.entity.odse.EntityIdId;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

@Component
class PatientIdentificationHistoryRecorder {

  private static final String INSERT_SNAPSHOT = """
      insert into Entity_id_hist (
          entity_uid,
          entity_id_seq,
          version_ctrl_nbr,
          add_reason_cd,
          add_time,
          add_user_id,
          assigning_authority_cd,
          assigning_authority_desc_txt,
          duration_amt,
          duration_unit_cd,
          effective_from_time,
          effective_to_time,
          last_chg_reason_cd,
          last_chg_time,
          last_chg_user_id,
          record_status_cd,
          record_status_time,
          root_extension_txt,
          status_cd,
          status_time,
          type_cd,
          type_desc_txt,
          user_affiliation_txt,
          valid_from_time,
          valid_to_time,
          as_of_date,
          assigning_authority_id_type
      )
      select
          entity_uid,
          entity_id_seq,
          (select count(*) FROM Entity_id_hist WHERE entity_uid = :patient and entity_id_seq = :sequence),
          add_reason_cd,
          add_time,
          add_user_id,
          assigning_authority_cd,
          assigning_authority_desc_txt,
          duration_amt,
          duration_unit_cd,
          effective_from_time,
          effective_to_time,
          last_chg_reason_cd,
          last_chg_time,
          last_chg_user_id,
          record_status_cd,
          record_status_time,
          root_extension_txt,
          status_cd,
          status_time,
          type_cd,
          type_desc_txt,
          user_affiliation_txt,
          valid_from_time,
          valid_to_time,
          as_of_date,
          assigning_authority_id_type
      from Entity_id
      where entity_uid = :patient
        and entity_id_seq = :sequence
      """;

  private final JdbcClient client;

  PatientIdentificationHistoryRecorder(final JdbcClient client) {
    this.client = client;
  }

  void snapshot(final EntityIdId identifier) {
    this.client.sql(INSERT_SNAPSHOT)
        .param("patient", identifier.getEntityUid())
        .param("sequence", identifier.getEntityIdSeq())
        .update();
  }
}
