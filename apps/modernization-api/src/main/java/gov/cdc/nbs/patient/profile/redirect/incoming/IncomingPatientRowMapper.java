package gov.cdc.nbs.patient.profile.redirect.incoming;

import gov.cdc.nbs.patient.identifier.PatientShortIdentifierResolver;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
class IncomingPatientRowMapper implements RowMapper<Optional<IncomingPatient>> {

  private static final int LOCAL_ID_COLUMN = 1;
  private final PatientShortIdentifierResolver resolver;

  IncomingPatientRowMapper(final PatientShortIdentifierResolver resolver) {
    this.resolver = resolver;
  }

  //  Suppressing java:S2638 (Method overrides should not change contracts), which is being
  //  thrown by Sonar because the parent class' `mapRow` is annotated with `@Nullable` while
  //  this subclass uses `Optional`, which is probably preferable tbh.  But also given they're
  //  effectively the same thing, and we don't control the superclass, AND adding `@Nullable`
  //  results in java:S2789 (Methods with an "Optional" return type should not be "@Nullable".),
  //  suppressing the warning seems like the best option.
  @SuppressWarnings("java:S2638")
  @Override
  public Optional<IncomingPatient> mapRow(final ResultSet rs, final int row) throws SQLException {
    String local = rs.getString(LOCAL_ID_COLUMN);

    try {
      long identifier = resolver.resolve(local).orElseThrow();
      return Optional.of(new IncomingPatient(identifier));
    } catch (NoSuchElementException exception) {
      return Optional.empty();
    }
  }
}
