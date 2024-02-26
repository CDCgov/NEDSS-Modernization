package gov.cdc.nbs.patient;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class PatientRaceHistoryCreator {
    private static final int VERSION_PARAMETER = 1;
    private static final int PATIENT_PARAMETER = 2;
    private static final int RACE_CODE_PARAMETER = 3;
    private final JdbcTemplate template;

    private static final String CREATE_PERSON_RACE_HIST = """
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
            person_uid,
            race_cd,
            ?,
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
        from Person_race
        where person_uid = ?
          and race_cd = ?
        """;

    public PatientRaceHistoryCreator(JdbcTemplate template) {
        this.template = template;
    }

    public void createPersonRaceHistory(final long person, final String raceCode, final int version) {
        this.template.update(
                CREATE_PERSON_RACE_HIST,
                statement -> {
                    statement.setInt(VERSION_PARAMETER, version);
                    statement.setLong(PATIENT_PARAMETER, person);
                    statement.setString(RACE_CODE_PARAMETER, raceCode);
                }
        );
    }
}
