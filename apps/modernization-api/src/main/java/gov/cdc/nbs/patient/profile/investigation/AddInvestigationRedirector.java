package gov.cdc.nbs.patient.profile.investigation;

import gov.cdc.nbs.patient.profile.redirect.outgoing.ClassicPatientProfileRedirector;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import java.net.URISyntaxException;

import java.net.URI;

@Hidden
@RestController
class AddInvestigationRedirector {

  private static final String LOCATION = "/nbs/LoadSelectCondition1.do?ContextAction=AddInvestigation";

  private final ClassicPatientProfileRedirector redirector;

  AddInvestigationRedirector(final ClassicPatientProfileRedirector redirector) {
    this.redirector = redirector;
  }

  @PreAuthorize("hasAuthority('ADD-INVESTIGATION')")
  @GetMapping("/nbs/api/profile/{patient}/investigation")
  @ResponseStatus(value = HttpStatus.TEMPORARY_REDIRECT)
  ResponseEntity<Void> add(@PathVariable final long patient) throws URISyntaxException {
    URI uri = new URI(LOCATION);

    return redirector.preparedRedirect(patient, uri);
  }
}

