package gov.cdc.nbs.patient;

import gov.cdc.nbs.entity.odse.PersonEthnicGroup;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import jakarta.persistence.PreRemove;

@Component
public class PatientEthnicityHistoryListener {

    private static final String QUERY =
        "SELECT MAX(version_ctrl_nbr) FROM Person_ethnic_group_hist WHERE person_uid = ? and ethnic_group_cd = ?";
    private static final int IDENTIFIER_PARAMETER = 1;
    private static final int GROUP_PARAMETER = 2;

    private final PatientEthnicityHistoryCreator creator;
    private final JdbcTemplate template;

    public PatientEthnicityHistoryListener(PatientEthnicityHistoryCreator creator, JdbcTemplate template) {
        this.creator = creator;
        this.template = template;
    }

    @PreRemove
    @SuppressWarnings(
        //  The PatientEthnicityHistoryListener is an entity listener specifically for instances of PersonEthnicGroup
        {"javaarchitecture:S7027","javaarchitecture:S7091"}
    )
    void preRemove(final PersonEthnicGroup personEthnicGroup) {
        long personUid = personEthnicGroup.getPersonUid().id();
        String personEthnicityGroupCd = personEthnicGroup.getId().getEthnicGroupCd();
        int currentVersion = getCurrentVersionNumber(personUid, personEthnicityGroupCd);
        this.creator.createPersonEthnicityLocatorHistory(personUid, personEthnicityGroupCd, currentVersion + 1);

    }

    private int getCurrentVersionNumber(final long identifier, final String group) {
        return template.query(
                QUERY, statement -> {
                    statement.setLong(IDENTIFIER_PARAMETER, identifier);
                    statement.setString(GROUP_PARAMETER, group);
                },
                (resultSet, row) -> resultSet.getInt(1))
            .stream()
            .findFirst()
            .orElse(0);
    }
}
