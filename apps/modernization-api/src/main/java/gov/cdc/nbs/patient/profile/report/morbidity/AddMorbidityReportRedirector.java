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
class AddMorbidityReportRedirector {

  private static final String LOCATION = "/nbs/ViewFile1.do";

  private final ClassicPatientProfileRedirector redirector;

  AddMorbidityReportRedirector(final ClassicPatientProfileRedirector redirector) {
    this.redirector = redirector;
  }

  @PreAuthorize("hasAuthority('ADD-OBSERVATIONMORBIDITYREPORT')")
  @GetMapping("/nbs/api/profile/{patient}/report/morbidity")
  ResponseEntity<Void> add(@PathVariable final long patient) {
    URI location =
        UriComponentsBuilder.fromPath(LOCATION)
            .queryParam("ContextAction", "AddMorb")
            .build()
            .toUri();

    return redirector.preparedRedirect(patient, location);
  }
}
