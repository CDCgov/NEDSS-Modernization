package gov.cdc.nbs.patient.profile.redirect.incoming;

import gov.cdc.nbs.patient.profile.redirect.PatientActionCookie;
import gov.cdc.nbs.patient.profile.redirect.ReturningPatientCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static gov.cdc.nbs.web.RemoveCookie.removeCookie;

public sealed interface ModernizedPatientProfileRedirect {

  static ModernizedPatientProfileRedirect forPatient(final IncomingPatient incoming) {
    return new ForPatient(incoming.identifier());
  }

  static ModernizedPatientProfileRedirect forPatient(final IncomingPatient incoming, final String tab) {
    return new ForPatient(incoming.identifier(), tab);
  }

  static ModernizedPatientProfileRedirect fallback() {
    return new Fallback();
  }

  ResponseEntity<Void> redirect();


  static ResponseEntity<Void> redirectTo(final URI location) {
    return ResponseEntity.status(HttpStatus.SEE_OTHER)
        .location(location)
        .headers(removeCookie(ReturningPatientCookie.empty().name()))
        .headers(removeCookie(PatientActionCookie.empty().name()))
        .build();
  }

  record ForPatient(long identifier, String tab) implements ModernizedPatientProfileRedirect {

    ForPatient(long identifier) {
      this(identifier, "summary");
    }

    @Override
    public ResponseEntity<Void> redirect() {
      URI uri = UriComponentsBuilder.fromPath("/")
          .path("patient/{identifier}/{tab}")
          .buildAndExpand(identifier(), tab())
          .toUri();

      return redirectTo(uri);
    }
  }


  record Fallback() implements ModernizedPatientProfileRedirect {

    @Override
    public ResponseEntity<Void> redirect() {
      URI uri = UriComponentsBuilder.fromPath("/")
          .path("search")
          .build()
          .toUri();
      return redirectTo(uri);
    }
  }
}
