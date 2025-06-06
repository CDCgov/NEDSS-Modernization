package gov.cdc.nbs.patient.profile.redirect.outgoing;

import gov.cdc.nbs.patient.profile.redirect.ReturningPatientCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.net.URI;

@Component
public class ClassicPatientProfileRedirector {

  private final ClassicPatientSearchPreparer searchPreparer;
  private final ClassicPatientProfilePreparer profilePreparer;

  ClassicPatientProfileRedirector(
      final ClassicPatientSearchPreparer searchPreparer,
      final ClassicPatientProfilePreparer profilePreparer
  ) {
    this.searchPreparer = searchPreparer;
    this.profilePreparer = profilePreparer;
  }

  public ResponseEntity<Void> preparedRedirect(final long patient, final URI location) {
    prepare(patient);
    return ResponseEntity
        .status(HttpStatus.TEMPORARY_REDIRECT)
        .location(location)
        .headers(new ReturningPatientCookie(patient).apply())
        .build();
  }

  private void prepare(final long patient) {
    searchPreparer.prepare();
    profilePreparer.prepare(patient);
  }
}
