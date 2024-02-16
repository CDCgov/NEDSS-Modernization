package gov.cdc.nbs.patient;

import gov.cdc.nbs.entity.odse.EntityId;
import gov.cdc.nbs.entity.odse.EntityIdId;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.persistence.PreUpdate;

@Component
public class PatientIdentificationHistoryListener {
    private final PatientIdentificationHistoryCreator creator;
    private final JdbcTemplate template;

    public PatientIdentificationHistoryListener(PatientIdentificationHistoryCreator creator, JdbcTemplate template) {
        this.creator = creator;
        this.template = template;
    }

    @PreUpdate
    void preUpdate(final EntityId entityId) {
        int entityIdSequence = entityId.getId().getEntityIdSeq();
        System.out.println("personNameSequence is..." + entityIdSequence);
        int currentVersion = getCurrentVersionNumber(entityId.getId(), entityIdSequence);
        this.creator.createEntityIdHistory(entityId.getId().getEntityUid(), currentVersion + 1, entityIdSequence);

    }

    private int getCurrentVersionNumber(EntityIdId id, int entityIdSequence) {
        long entityUid = id.getEntityUid();
        String query = "SELECT MAX(version_ctrl_nbr) FROM Entity_id_hist WHERE entity_uid = ? and entity_id_seq = ?";
        Integer maxVersionControlNumber = template.queryForObject(query, Integer.class, entityUid, entityIdSequence);
        System.out.println("maxVersionControlNumber is..." + maxVersionControlNumber);
        return maxVersionControlNumber != null ? maxVersionControlNumber : 0;
    }
}
