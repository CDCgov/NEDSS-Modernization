package gov.cdc.nbs.patient.profile.redirect.outgoing;

import gov.cdc.nbs.patient.profile.redirect.PatientActionCookie;
import gov.cdc.nbs.patient.profile.redirect.ReturningPatientCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static gov.cdc.nbs.web.RemoveCookie.removeCookie;

@Component
public class NBS6PatientFileRedirectResolver {

  private final ClassicPatientSearchPreparer preparer;

  NBS6PatientFileRedirectResolver(final ClassicPatientSearchPreparer preparer) {
    this.preparer = preparer;
  }

  public ResponseEntity<Void> resolve(final long patient) {

    preparer.prepare();

    URI location = UriComponentsBuilder.fromPath("/nbs")
        .path("PatientSearchResults1.do?ContextAction=ViewFile&uid=")
        .queryParam("ContextAction", "ViewFile")
        .queryParam("uid", patient)
        .build()
        .toUri();

    return ResponseEntity.status(HttpStatus.SEE_OTHER)
        .location(location)
        .headers(removeCookie(ReturningPatientCookie.empty().name()))
        .headers(removeCookie(PatientActionCookie.empty().name()))
        .build();
  }

}
