package gov.cdc.nbs.patient;

import gov.cdc.nbs.entity.odse.PersonEthnicGroup;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import jakarta.persistence.PreRemove;

@Component
public class PatientEthnicityHistoryListener {
    private final PatientEthnicityHistoryCreator creator;
    private final JdbcTemplate template;

    public PatientEthnicityHistoryListener(PatientEthnicityHistoryCreator creator, JdbcTemplate template) {
        this.creator = creator;
        this.template = template;
    }

    @PreRemove
    void preRemove(final PersonEthnicGroup personEthnicGroup) {
        long personUid = personEthnicGroup.getPersonUid().getId();
        String personEthnicityGroupCd = personEthnicGroup.getId().getEthnicGroupCd();
        int currentVersion = getCurrentVersionNumber(personUid, personEthnicityGroupCd);
        this.creator.createPersonEthnicityLocatorHistory(personUid, personEthnicityGroupCd, currentVersion + 1);

    }

    private int getCurrentVersionNumber(long personUid, String ethnicityGroupCd) {
        String query = "SELECT MAX(version_ctrl_nbr) FROM Person_ethnic_group_hist WHERE person_uid = ? and ethnic_group_cd = ?";
        Integer maxVersionControlNumber = template.queryForObject(query, Integer.class, personUid, ethnicityGroupCd);
        return maxVersionControlNumber != null ? maxVersionControlNumber : 0;
    }
}
