package gov.cdc.nbs.patient;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class PatientIdentificationHistoryCreator {
    private static final int VERSION_PARAMETER = 1;
    private static final int ENTITY_PARAMETER = 2;
    private static final int SEQUENCE_PARAMETER = 3;
    private final JdbcTemplate template;

    private static final String CREATE_ENTITY_ID_HIST = """
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
            ?,
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
        where entity_uid = ?
          and entity_id_seq = ?
        """;

    public PatientIdentificationHistoryCreator(JdbcTemplate template) {
        this.template = template;
    }

    public void createEntityIdHistory(final long entity, final int version, final int sequence) {
        this.template.update(
                CREATE_ENTITY_ID_HIST,
                statement -> {
                    statement.setInt(VERSION_PARAMETER, version);
                    statement.setLong(ENTITY_PARAMETER, entity);
                    statement.setInt(SEQUENCE_PARAMETER, sequence);
                }
        );
    }
}
