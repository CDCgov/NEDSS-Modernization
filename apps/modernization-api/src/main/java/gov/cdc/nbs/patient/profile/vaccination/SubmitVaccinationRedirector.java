package gov.cdc.nbs.patient.profile.vaccination;

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
class SubmitVaccinationRedirector {

  private static final String LOCATION = "/nbs/PageAction.do";

  private final ClassicPatientProfileRedirector redirector;

  SubmitVaccinationRedirector(final ClassicPatientProfileRedirector redirector) {
    this.redirector = redirector;
  }

  @PreAuthorize("hasAuthority('ADD-INTERVENTIONVACCINERECORD')")
  @GetMapping("/nbs/api/profile/{patient}/vaccination")
  ResponseEntity<Void> view(@PathVariable final long patient) {

    URI location =
        UriComponentsBuilder.fromPath(LOCATION)
            .queryParam("method", "createGenericLoad")
            .queryParam("businessObjectType", "VAC")
            .queryParam("Action", "DSFilePath")
            .build()
            .toUri();

    return redirector.preparedRedirect(patient, location);
  }
}
