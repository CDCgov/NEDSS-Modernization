package gov.cdc.nbs.patient;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class PatientIdentificationHistoryCreator {
    private static final int VERSION_PARAMETER = 1;
    private static final int ENTITY_PARAMETER = 2;
    private static final int SEQUENCE_PARAMETER = 3;
    private final JdbcTemplate template;

    public PatientIdentificationHistoryCreator(JdbcTemplate template) {
        this.template = template;
    }

    public void createEntityIdHistory(final long entity, final int version, final int sequence) {
        this.template.update(
                CREATE_ENTITY_ID_HIST,
                statement -> {
                    statement.setInt(VERSION_PARAMETER, version);
                    statement.setLong(ENTITY_PARAMETER, entity);
                    statement.setInt(SEQUENCE_PARAMETER, sequence);
                }
        );
    }
}
