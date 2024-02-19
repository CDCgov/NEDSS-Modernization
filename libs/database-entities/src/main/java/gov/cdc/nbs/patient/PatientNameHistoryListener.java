package gov.cdc.nbs.patient;

import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.entity.odse.PersonName;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.persistence.PreUpdate;

@Component
public class PatientNameHistoryListener {
    private PatientNameHistoryCreator creator;
    private final JdbcTemplate template;

    public PatientNameHistoryListener(final PatientNameHistoryCreator creator, JdbcTemplate template) {
        this.creator = creator;
        this.template = template;
    }

    @PreUpdate
    void preUpdate(PersonName personName) {
        int personNameSequence = personName.getId().getPersonNameSeq();
        int currentVersion = getCurrentVersionNumber(personName.getPersonUid(), personNameSequence);
        this.creator.createPersonNameHistory(personName.getPersonUid().getId(), currentVersion + 1, personNameSequence);
    }

    private int getCurrentVersionNumber(Person id, int personNameSequence) {
        long personUid = id.getId();
        String query = "SELECT MAX(version_ctrl_nbr) FROM Person_name_hist WHERE person_uid = ? and person_name_seq = ?";
        Integer maxVersionControlNumber = template.queryForObject(query, Integer.class, personUid, personNameSequence);
        return maxVersionControlNumber != null ? maxVersionControlNumber : 0;
    }
}
