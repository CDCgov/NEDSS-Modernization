package gov.cdc.nbs.patient.profile.redirect.incoming;

import gov.cdc.nbs.patient.identifier.PatientShortIdentifierResolver;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.NoSuchElementException;
import java.util.Optional;

@Component
public class IncomingPatientFromActionFinder {

  private static final String QUERY = """
       select
          [patient].[local_id]
      from person [patient]
          join participation [participation] on
                  [participation].record_status_cd = 'ACTIVE'
              and [participation].[subject_class_cd] = 'PSN'
              and [participation].[subject_entity_uid] = [patient].person_uid                 
      where  [participation].[act_uid] = ?
      """;

  private final JdbcTemplate template;
  private final PatientShortIdentifierResolver resolver;

  IncomingPatientFromActionFinder(
      final JdbcTemplate template,
      final PatientShortIdentifierResolver resolver
  ) {
    this.template = template;
    this.resolver = resolver;
  }

  public Optional<IncomingPatient> find(final long identifier) {
    try {
      return this.template.query(
              QUERY,
              setter -> setter.setLong(1, identifier),
              this::map
          ).stream()
          .findFirst();
    } catch (NoSuchElementException exception) {
      return Optional.empty();
    }
  }

  private IncomingPatient map(final ResultSet rs, final int row) throws SQLException {
    String local = rs.getString(1);

    long identifier = resolver.resolve(local).orElseThrow();
    return new IncomingPatient(identifier);
  }
}
