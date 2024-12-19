package gov.cdc.nbs.patient;

import gov.cdc.nbs.entity.odse.TeleEntityLocatorParticipation;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import jakarta.persistence.PreUpdate;

@Component
public class PatientPhoneLocatorHistoryListener {
    private final PatientPhoneLocatorHistoryCreator creator;
    private final JdbcTemplate template;

    public PatientPhoneLocatorHistoryListener(PatientPhoneLocatorHistoryCreator creator, JdbcTemplate template) {
        this.creator = creator;
        this.template = template;
    }

    @PreUpdate
    @SuppressWarnings(
        //  The PatientPhoneLocatorHistoryListener is an entity listener specifically for instances of TeleEntityLocatorParticipation
        {"javaarchitecture:S7027","javaarchitecture:S7091"}
    )
    void preUpdate(final TeleEntityLocatorParticipation teleEntityLocatorParticipation) {
        long locatorId = teleEntityLocatorParticipation.getId().getLocatorUid();
        int currentVersion = getCurrentVersionNumber(locatorId);
        this.creator.createTeleLocatorHistory(locatorId, currentVersion + 1);
    }

    private int getCurrentVersionNumber(long locatorId) {
        String query = "SELECT MAX(version_ctrl_nbr) FROM Tele_locator_hist WHERE tele_locator_uid = ?";
        Integer maxVersionControlNumber = template.queryForObject(query, Integer.class, locatorId);
        return maxVersionControlNumber != null ? maxVersionControlNumber : 0;
    }
}
