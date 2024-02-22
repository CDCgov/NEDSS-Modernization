package gov.cdc.nbs.patient;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class PatientPhoneLocatorHistoryCreator {
    private static final int VERSION_PARAMETER = 1;
    private static final int LOCATOR_PARAMETER = 2;
    private final JdbcTemplate template;

    private static final String CREATE_TELE_LOCATOR_HIST = """
        insert into Tele_locator_hist (
            tele_locator_uid,
            version_ctrl_nbr,
            add_reason_cd,
            add_time,
            add_user_id,
            cntry_cd,
            email_address,
            extension_txt,
            last_chg_reason_cd,
            last_chg_time,
            last_chg_user_id,
            phone_nbr_txt,
            record_status_cd,
            record_status_time,
            url_address,
            user_affiliation_txt
        )
        select
            tele_locator_uid,
            ?,
            add_reason_cd,
            add_time,
            add_user_id,
            cntry_cd,
            email_address,
            extension_txt,
            last_chg_reason_cd,
            last_chg_time,
            last_chg_user_id,
            phone_nbr_txt,
            record_status_cd,
            record_status_time,
            url_address,
            user_affiliation_txt
        from Tele_locator
        where tele_locator_uid = ?
    """;

    public PatientPhoneLocatorHistoryCreator(JdbcTemplate template) {
        this.template = template;
    }

    public void createTeleLocatorHistory(long locatorId, int version) {
        this.template.update(
                CREATE_TELE_LOCATOR_HIST,
                statement -> {
                    statement.setInt(VERSION_PARAMETER, version);
                    statement.setLong(LOCATOR_PARAMETER, locatorId);
                }
        );
    }
}
