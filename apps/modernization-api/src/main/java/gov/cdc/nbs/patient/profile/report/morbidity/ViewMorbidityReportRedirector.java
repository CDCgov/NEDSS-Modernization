package gov.cdc.nbs.patient.profile.report.morbidity;

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
class ViewMorbidityReportRedirector {

  private static final String LOCATION = "/nbs/ViewFile1.do";

  private final ClassicPatientProfileRedirector redirector;

  ViewMorbidityReportRedirector(final ClassicPatientProfileRedirector redirector) {
    this.redirector = redirector;
  }

  @PreAuthorize("hasAuthority('VIEW-OBSERVATIONMORBIDITYREPORT')")
  @GetMapping("/nbs/api/profile/{patient}/report/morbidity/{identifier}")
  ResponseEntity<Void> view(@PathVariable final long patient, @PathVariable final long identifier) {

    URI location =
        UriComponentsBuilder.fromPath(LOCATION)
            .queryParam("ContextAction", "ObservationMorbIDOnSummary")
            .queryParam("observationUID", identifier)
            .build()
            .toUri();

    return redirector.preparedRedirect(patient, location);
  }
}
