package gov.cdc.nbs.patient.profile.investigation.delete;

import gov.cdc.nbs.patient.profile.redirect.incoming.ModernizedPatientProfileRedirectResolver;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Hidden
@RestController
class DeleteInvestigationRedirector {

  private static final String LOCATION = "/PageAction.do";

  private final RestTemplate template;
  private final ModernizedPatientProfileRedirectResolver resolver;

  DeleteInvestigationRedirector(
      @Qualifier("classicTemplate") final RestTemplate template,
      final ModernizedPatientProfileRedirectResolver resolver) {
    this.template = template;
    this.resolver = resolver;
  }

  @PostMapping(
      path = "/nbs/redirect/patient/investigation/delete",
      consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
  ResponseEntity<Void> deleted(
      final HttpServletRequest request,
      @RequestParam final MultiValueMap<String, String> data) {

    deleteInvestigation(data);

    return resolver.fromReturnPatient(request);
  }

  private void deleteInvestigation(final MultiValueMap<String, String> data) {
    RequestEntity<MultiValueMap<String, String>> request = RequestEntity
        .post(LOCATION)
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .body(data);

    this.template.exchange(request, Void.class);
  }

}
