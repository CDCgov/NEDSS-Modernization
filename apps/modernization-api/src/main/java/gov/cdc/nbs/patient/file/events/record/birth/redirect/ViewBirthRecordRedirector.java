package gov.cdc.nbs.patient.file.events.record.birth.redirect;

import gov.cdc.nbs.patient.profile.redirect.outgoing.ClassicPatientProfileRedirector;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Hidden
@RestController
class ViewBirthRecordRedirector {

  private static final String LOCATION = "/nbs/PageAction.do";

  private final ClassicPatientProfileRedirector redirector;

  ViewBirthRecordRedirector(final ClassicPatientProfileRedirector redirector) {
    this.redirector = redirector;
  }

  @PreAuthorize("hasAuthority('VIEW-BIRTHRECORD')")
  @GetMapping("/nbs/api/patients/{patient}/records/birth/{identifier}")
  ResponseEntity<Void> view(
      @PathVariable final long patient,
      @PathVariable final long identifier
  ) {

    URI location = UriComponentsBuilder.fromPath(LOCATION)
        .queryParam("method", "viewGenericLoad")
        .queryParam("businessObjectType", "BIR")
        .queryParam("actUid", identifier)
        .build()
        .toUri();

    return redirector.preparedRedirect(patient, location);
  }


}
