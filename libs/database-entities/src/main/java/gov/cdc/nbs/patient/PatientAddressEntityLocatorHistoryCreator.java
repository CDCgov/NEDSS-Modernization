package gov.cdc.nbs.patient;

import org.springframework.stereotype.Component;
import org.springframework.jdbc.core.JdbcTemplate;

@Component
public class PatientAddressEntityLocatorHistoryCreator {
    private static final int ENTITY_PARAMETER = 1;
    private static final int LOCATOR_PARAMETER = 2;
    private static final int VERSION_PARAMETER = 3;
    private final JdbcTemplate template;
    private static final String CREATE_ENTITY_LOCATOR_HIST = """
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
        where entity_uid = ?
            and locator_uid = ?
            and version_ctrl_nbr = ?
        """;

    public PatientAddressEntityLocatorHistoryCreator(JdbcTemplate template) {
        this.template = template;
    }

    public void createEntityLocatorHistory(Long entityUid, Long locatorUid, int version) {
        this.template.update(
                CREATE_ENTITY_LOCATOR_HIST,
                statement -> {
                    statement.setLong(ENTITY_PARAMETER, entityUid);
                    statement.setLong(LOCATOR_PARAMETER, locatorUid);
                    statement.setInt(VERSION_PARAMETER, version);
                });
    }
}
