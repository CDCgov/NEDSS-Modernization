package gov.cdc.nbs.patient;

import gov.cdc.nbs.entity.odse.PersonRace;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import jakarta.persistence.PreRemove;

@Component
public class PatientRaceHistoryListener {
    private final PatientRaceHistoryCreator creator;
    private final JdbcTemplate template;

    public PatientRaceHistoryListener(PatientRaceHistoryCreator creator, JdbcTemplate template) {
        this.creator = creator;
        this.template = template;
    }

    @PreRemove
    @SuppressWarnings({"javaarchitecture:S7027","javaarchitecture:S7091"})
    void preRemove(final PersonRace personRace) {
        long personUid = personRace.getPersonUid().getId();
        String raceCode = personRace.getRaceCd();
        int currentVersion = getCurrentVersionNumber(personUid, raceCode);
        this.creator.createPersonRaceHistory(personUid, raceCode, currentVersion + 1);
    }

    private int getCurrentVersionNumber(long personUid, String raceCode) {
        String query = "SELECT MAX(version_ctrl_nbr) FROM Person_race_hist WHERE person_uid = ? and race_cd = ?";
        Integer maxVersionControlNumber = template.queryForObject(query, Integer.class, personUid, raceCode);
        return maxVersionControlNumber != null ? maxVersionControlNumber : 0;
    }
}
