package gov.cdc.nbs.patient;

import gov.cdc.nbs.entity.odse.EntityId;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import jakarta.persistence.PreUpdate;

@Component
public class PatientIdentificationHistoryListener {

    private static final String QUERY = "SELECT MAX(version_ctrl_nbr) FROM Entity_id_hist WHERE entity_uid = ? and entity_id_seq = ?";
    private static final int IDENTIFIER_PARAMETER = 1;
    private static final int SEQUENCE_PARAMETER = 2;

    private final PatientIdentificationHistoryCreator creator;
    private final JdbcTemplate template;

    public PatientIdentificationHistoryListener(PatientIdentificationHistoryCreator creator, JdbcTemplate template) {
        this.creator = creator;
        this.template = template;
    }

    @PreUpdate
    @SuppressWarnings(
        //  The PatientIdentificationHistoryListener is an entity listener specifically for instances of EntityId
        {"javaarchitecture:S7027", "javaarchitecture:S7091"}
    )
    void preUpdate(final EntityId entityId) {
        long identifier = entityId.getId().getEntityUid();
        int sequence = entityId.getId().getEntityIdSeq();
        int currentVersion = getCurrentVersionNumber(identifier, sequence);
        this.creator.createEntityIdHistory(identifier, currentVersion + 1, sequence);

    }

    private int getCurrentVersionNumber(long identifier, int sequence) {
        return template.query(
                QUERY, statement -> {
                    statement.setLong(IDENTIFIER_PARAMETER, identifier);
                    statement.setInt(SEQUENCE_PARAMETER, sequence);
                },
                (resultSet, row) -> resultSet.getInt(1))
            .stream()
            .findFirst()
            .orElse(0);
    }
}
