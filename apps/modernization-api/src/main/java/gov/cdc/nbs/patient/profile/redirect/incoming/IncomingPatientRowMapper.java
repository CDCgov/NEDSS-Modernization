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
