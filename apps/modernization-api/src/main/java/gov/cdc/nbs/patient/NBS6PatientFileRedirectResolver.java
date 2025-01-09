package gov.cdc.nbs.patient;

import gov.cdc.nbs.patient.profile.redirect.outgoing.ClassicPatientSearchPreparer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
public class NBS6PatientFileRedirectResolver {

  private final ClassicPatientSearchPreparer preparer;

  NBS6PatientFileRedirectResolver(final ClassicPatientSearchPreparer preparer) {
    this.preparer = preparer;
  }

  /**
   * Resolves a Response that redirects the caller to the NBS6 Patient File
   *
   * @param patient The unique identifier of a patient
   * @return The redirecting {@link ResponseEntity}
   */
  public ResponseEntity<Void> resolve(final long patient) {

    //  Issues the appropriate requests to put the NBS6 session in a state that allows a user to access a Patient File.
    preparer.prepare();

    URI location = patientFilePath(patient);

    return ResponseEntity.status(HttpStatus.SEE_OTHER)
        .location(location)
        .build();
  }

  private URI patientFilePath(final long patient) {
    return UriComponentsBuilder.fromPath("/nbs")
        .path("/PatientSearchResults1.do")
        .queryParam("ContextAction", "ViewFile")
        .queryParam("uid", patient)
        .build()
        .toUri();
  }

}
