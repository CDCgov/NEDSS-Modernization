package gov.cdc.nbs.patient;

import gov.cdc.nbs.entity.odse.PersonName;
import jakarta.persistence.PreUpdate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class PatientNameHistoryListener {

  private static final String QUERY =
      "SELECT max(version_ctrl_nbr) FROM Person_name_hist WHERE person_uid = ? and person_name_seq = ?";
  private static final int IDENTIFIER_PARAMETER = 1;
  private static final int SEQUENCE_PARAMETER = 2;

  private final PatientNameHistoryCreator creator;
  private final JdbcTemplate template;

  public PatientNameHistoryListener(final PatientNameHistoryCreator creator, JdbcTemplate template) {
    this.creator = creator;
    this.template = template;
  }

  @PreUpdate
  @SuppressWarnings(
      //  The PatientNameHistoryListener is an entity listener specifically for instances of PersonName
      {"javaarchitecture:S7027", "javaarchitecture:S7091"}
  )
  void preUpdate(final PersonName personName) {
    long identifier = personName.getPersonUid().getId();
    int sequence = personName.getId().getPersonNameSeq();
    int currentVersion = getCurrentVersionNumber(identifier, sequence);
    this.creator.createPersonNameHistory(identifier, currentVersion + 1, sequence);
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
