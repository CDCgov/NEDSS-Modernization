package gov.cdc.nbs.patient;

import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.entity.odse.PersonName;
import gov.cdc.nbs.entity.odse.PersonNameId;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.persistence.Embedded;
import javax.persistence.PreUpdate;
import javax.persistence.Transient;
import javax.transaction.Transactional;

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
//        int personNameSequence = personNameId.getPersonNameSeq();
        int personNameSequence = personName.getId().getPersonNameSeq();
        System.out.println("personNameSequence is..." + personNameSequence);
        int currentVersion = getCurrentVersionNumber(personName.getPersonUid(), personNameSequence);
        System.out.println("currentVersion is..." + currentVersion);
        this.creator.createPersonNameHistory(personName.getPersonUid().getId(), currentVersion + 1, personNameSequence);
    }

    @Transactional
    int getCurrentVersionNumber(Person id, int personNameSequence) {
        long personUid = id.getId();
        String query = "SELECT MAX(version_ctrl_nbr) FROM Person_name_hist WHERE person_uid = ? and person_name_seq = ?";
        Integer maxVersionControlNumber = template.queryForObject(query, Integer.class, personUid, personNameSequence);
        System.out.println("maxVersionControlNumber is..." + maxVersionControlNumber);
        return maxVersionControlNumber != null ? maxVersionControlNumber : 0;
    }
}
