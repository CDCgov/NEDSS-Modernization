package gov.cdc.nbs.patient.profile.investigation.compare;

import gov.cdc.nbs.patient.profile.redirect.outgoing.ClassicPatientProfileRedirector;
import io.swagger.v3.oas.annotations.Hidden;
import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@Hidden
@RestController
class CompareInvestigationRedirector {

  private static final String LOCATION = "/nbs/ViewFile1.do";

  private final ClassicPatientProfileRedirector redirector;

  CompareInvestigationRedirector(final ClassicPatientProfileRedirector redirector) {
    this.redirector = redirector;
  }

  @PreAuthorize("hasAuthority('MERGEINVESTIGATION-INVESTIGATION')")
  @GetMapping("/nbs/api/profile/{patient}/investigation/{investigation}/compare/{other}")
  ResponseEntity<Void> view(
      @PathVariable final long patient,
      @PathVariable final long investigation,
      @PathVariable final long other) {

    URI location =
        UriComponentsBuilder.fromPath(LOCATION)
            .queryParam("ContextAction", "CompareInvestigations")
            .queryParam("publicHealthCaseUID0", investigation)
            .queryParam("publicHealthCaseUID1", other)
            .build()
            .toUri();

    return redirector.preparedRedirect(patient, location);
  }
}
