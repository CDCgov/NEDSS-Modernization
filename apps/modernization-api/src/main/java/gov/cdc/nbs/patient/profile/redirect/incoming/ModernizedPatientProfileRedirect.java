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

  static ModernizedPatientProfileRedirect fallback() {
    return new Fallback();
  }

  ResponseEntity<Void> redirect();


  static ResponseEntity<Void> redirectTo(final URI location) {
    return ResponseEntity.status(HttpStatus.SEE_OTHER)
        .location(location)
        .headers(
            ReturningPatientCookie.empty().apply()
                .andThen(removeCookie(PatientActionCookie.empty().name()))
        )
        .build();
  }

  record ForPatient(long identifier) implements ModernizedPatientProfileRedirect {

    @Override
    public ResponseEntity<Void> redirect() {
      URI uri = UriComponentsBuilder.fromPath("/")
          .path("patient-profile/{identifier}")
          .buildAndExpand(identifier())
          .toUri();

      return redirectTo(uri);
    }
  }


  record Fallback() implements ModernizedPatientProfileRedirect {

    @Override
    public ResponseEntity<Void> redirect() {
      URI uri = UriComponentsBuilder.fromPath("/")
          .path("advanced-search")
          .build()
          .toUri();
      return redirectTo(uri);
    }
  }
}
