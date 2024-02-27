package gov.cdc.nbs.patient;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class PatientEthnicityHistoryCreator {
    private static final int VERSION_PARAMETER = 1;
    private static final int PATIENT_PARAMETER = 2;
    private static final int GROUP_CODE_PARAMETER = 3;
    private final JdbcTemplate template;
    private static final String CREATE_ETHNICITY_LOCATOR_HIST = """
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
            ?,
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
        where person_uid = ?
        and ethnic_group_cd = ?
    """;

    public PatientEthnicityHistoryCreator(JdbcTemplate template) {
        this.template = template;
    }

    public void createPersonEthnicityLocatorHistory(long personUid, String personEthnicityGroupCd, int version) {
        this.template.update(
                CREATE_ETHNICITY_LOCATOR_HIST,
                statement -> {
                    statement.setInt(VERSION_PARAMETER, version);
                    statement.setLong(PATIENT_PARAMETER, personUid);
                    statement.setString(GROUP_CODE_PARAMETER, personEthnicityGroupCd);
                }
        );
    }
}
