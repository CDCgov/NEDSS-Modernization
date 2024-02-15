package gov.cdc.nbs.patient;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class PatientNameHistoryCreator {

    private static final int VERSION_PARAMETER = 1;
    private static final int PATIENT_PARAMETER = 2;
    private static final int SEQUENCE_PARAMETER = 3;
    private final JdbcTemplate template;

    public PatientNameHistoryCreator(final JdbcTemplate template) {
        this.template = template;
    }


    private static final String CREATE_PERSON_NAME_HIST = """
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
           ?, 
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
       where person_uid = ?
         and person_name_seq = ?
       """;
    public void createPersonNameHistory(final long patient, final int version, final int sequence) {
        this.template.update(
            CREATE_PERSON_NAME_HIST,
            statement -> {
                statement.setInt(VERSION_PARAMETER, version);
                statement.setLong(PATIENT_PARAMETER, patient);
                statement.setInt(SEQUENCE_PARAMETER, sequence);
            }
        );
    }
}
