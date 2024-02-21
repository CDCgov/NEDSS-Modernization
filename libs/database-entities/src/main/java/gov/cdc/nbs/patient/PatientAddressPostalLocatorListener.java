package gov.cdc.nbs.patient;

import gov.cdc.nbs.entity.odse.PostalEntityLocatorParticipation;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.persistence.PreUpdate;

@Component
public class PatientAddressPostalLocatorListener {
    private final PatientAddressPostalLocatorHistoryCreator creator;
    private final JdbcTemplate template;


    public PatientAddressPostalLocatorListener(PatientAddressPostalLocatorHistoryCreator creator, JdbcTemplate template) {
        this.creator = creator;
        this.template = template;
    }

    @PreUpdate
    void preUpdate(final PostalEntityLocatorParticipation postalEntityLocatorParticipation) {
        long locatorId = postalEntityLocatorParticipation.getId().getLocatorUid();
        int currentVersion = getCurrentVersionNumber(locatorId);
        this.creator.createPostalLocatorHistory(locatorId, currentVersion + 1);
    }

    private int getCurrentVersionNumber(long locatorId) {
        String query = "SELECT MAX(version_ctrl_nbr) FROM Postal_locator_hist WHERE postal_locator_uid = ?";
        Integer maxVersionControlNumber = template.queryForObject(query, Integer.class, locatorId);
        return maxVersionControlNumber != null ? maxVersionControlNumber : 0;
    }
}
