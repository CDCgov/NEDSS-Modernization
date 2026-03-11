package gov.cdc.nbs.patient.profile.report.morbidity;

import gov.cdc.nbs.patient.profile.redirect.incoming.ModernizedPatientProfileRedirectResolver;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Hidden
@RestController
class SubmitMorbidityReportRedirector {

  private static final String SUBMIT_AND_CREATE_INVESTIGATION = "SubmitAndCreateInvestigation";
  private static final String LOCATION = "/AddObservationMorb2.do";

  private final RestTemplate template;
  private final ModernizedPatientProfileRedirectResolver resolver;

  SubmitMorbidityReportRedirector(
      @Qualifier("classicTemplate") final RestTemplate template,
      final ModernizedPatientProfileRedirectResolver resolver) {
    this.template = template;
    this.resolver = resolver;
  }

  @PostMapping(
      path = "/nbs/redirect/patient/report/morbidity/submit",
      consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
  ResponseEntity<Void> submitted(
      final HttpServletRequest request, @RequestParam final MultiValueMap<String, String> data) {

    if (shouldRedirectToClassic(data)) {
      return redirectToClassic();
    }

    createMorbidityReport(data);

    return resolver.fromReturnPatient(request);
  }

  private void createMorbidityReport(final MultiValueMap<String, String> data) {
    RequestEntity<MultiValueMap<String, String>> request =
        RequestEntity.post(LOCATION).contentType(MediaType.MULTIPART_FORM_DATA).body(data);

    this.template.exchange(request, Void.class);
  }

  private boolean shouldRedirectToClassic(final MultiValueMap<String, String> data) {
    List<String> actions = data.get("ContextAction");

    return actions != null && actions.contains(SUBMIT_AND_CREATE_INVESTIGATION);
  }

  private ResponseEntity<Void> redirectToClassic() {
    //  The user chose to Submit the Morbidity Report and then Create an Investigation.  This does
    // not navigate
    //  back to the Patient Profile so the request can be redirected back to classic.
    URI location =
        UriComponentsBuilder.fromPath("/nbs")
            .path(LOCATION)
            .queryParam("ContextAction", SUBMIT_AND_CREATE_INVESTIGATION)
            .build()
            .toUri();
    return ResponseEntity.status(HttpStatus.TEMPORARY_REDIRECT).location(location).build();
  }
}
