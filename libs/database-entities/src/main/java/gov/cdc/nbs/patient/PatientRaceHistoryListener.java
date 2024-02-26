package gov.cdc.nbs.patient;

import gov.cdc.nbs.entity.odse.Person;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.persistence.PreUpdate;

@Component
public class PatientRaceHistoryListener {
    private final PatientRaceHistoryCreator creator;
    private final JdbcTemplate template;

    public PatientRaceHistoryListener(PatientRaceHistoryCreator creator, JdbcTemplate template) {
        this.creator = creator;
        this.template = template;
    }

    @PreUpdate
    void preUpdate(final Person person) {
        long personId = person.getId();
        String raceCode = person.getRaceCd();
        int currentVersion = getCurrentVersionNumber(personId, raceCode);
        this.creator.createPersonRaceHistory(personId, raceCode, currentVersion + 1);
    }

    private int getCurrentVersionNumber(long personId, String raceCode) {
        String query = "SELECT MAX(version_ctrl_nbr) FROM Person_race_hist WHERE person_uid = ? and race_cd = ?";
        Integer maxVersionControlNumber = template.queryForObject(query, Integer.class, personId, raceCode);
        return maxVersionControlNumber != null ? maxVersionControlNumber : 0;
    }
}
