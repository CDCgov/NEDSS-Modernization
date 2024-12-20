package gov.cdc.nbs.patient;

import gov.cdc.nbs.entity.odse.PostalEntityLocatorParticipation;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import jakarta.persistence.PreUpdate;

@Component
public class PatientPostalLocatorHistoryListener {
    private final PatientPostalLocatorHistoryCreator creator;
    private final JdbcTemplate template;

    public PatientPostalLocatorHistoryListener(PatientPostalLocatorHistoryCreator creator, JdbcTemplate template) {
        this.creator = creator;
        this.template = template;
    }

    @PreUpdate
    @SuppressWarnings(
        //  The PatientPostalLocatorHistoryListener is an entity listener specifically for instances of PostalEntityLocatorParticipation
        {"javaarchitecture:S7027","javaarchitecture:S7091"}
    )
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
