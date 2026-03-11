package gov.cdc.nbs.patient.profile.investigation.delete;

import gov.cdc.nbs.patient.profile.redirect.incoming.ModernizedPatientProfileRedirectResolver;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Hidden
@RestController
class DeleteLegacyInvestigationRedirector {

  private static final String RETURN_TO_FILE_SUMMARY = "ReturnToFileSummary";
  private static final String CONTEXT_ACTION_PARAMETER = "ContextAction";
  private static final String DELETE_PARAMETER = "delete";
  private static final String RETURN_TO_FILE_EVENTS = "ReturnToFileEvents";

  private final RestTemplate template;
  private final ModernizedPatientProfileRedirectResolver resolver;
  private final DeletedInvestigationResponseHandler handler;

  DeleteLegacyInvestigationRedirector(
      @Qualifier("classicTemplate") final RestTemplate template,
      final ModernizedPatientProfileRedirectResolver resolver,
      final DeletedInvestigationResponseHandler handler) {
    this.template = template;
    this.resolver = resolver;
    this.handler = handler;
  }

  @GetMapping("/nbs/redirect/patient/investigation/delete")
  ResponseEntity<Void> deleted(
      final HttpServletRequest request,
      @RequestParam(
              required = false,
              name = CONTEXT_ACTION_PARAMETER,
              defaultValue = RETURN_TO_FILE_SUMMARY)
          final String action) {
    return deleteLegacyInvestigation(action).orElseGet(() -> resolver.fromReturnPatient(request));
  }

  private Optional<ResponseEntity<Void>> deleteLegacyInvestigation(final String action) {

    String location = resolveLocation(action);
    RequestEntity<Void> request = RequestEntity.post(location).build();

    ResponseEntity<Void> response = this.template.exchange(request, Void.class);

    return this.handler.handle(response);
  }

  private String resolveLocation(final String action) {
    String path = resolvePath(action);

    return UriComponentsBuilder.fromPath(path)
        .queryParam(CONTEXT_ACTION_PARAMETER, action)
        .queryParam(DELETE_PARAMETER, "true")
        .build()
        .toUriString();
  }

  private String resolvePath(final String action) {
    return switch (action) {
      case RETURN_TO_FILE_SUMMARY -> "/ViewInvestigation1.do";
      case RETURN_TO_FILE_EVENTS -> "/ViewInvestigation3.do";
      default -> "/ViewInvestigation2.do";
    };
  }
}
