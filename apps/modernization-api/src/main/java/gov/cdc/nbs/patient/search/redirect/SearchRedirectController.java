package gov.cdc.nbs.patient.search.redirect;

import gov.cdc.nbs.encryption.EncryptionService;
import gov.cdc.nbs.event.search.InvestigationFilter;
import gov.cdc.nbs.redirect.search.EventFilterResolver;
import gov.cdc.nbs.redirect.search.PatientFilterFromRequestParamResolver;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Map;

@RestController
class SearchRedirectController {
  private final SearchRedirect searchRedirect;

  private final PatientFilterFromRequestParamResolver patientFilterFromRequestParamResolver;

  private final EventFilterResolver eventFilterResolver;

  private final EncryptionService encryptionService;

  SearchRedirectController(
      final SearchRedirect searchRedirect,
      final PatientFilterFromRequestParamResolver patientFilterFromRequestParamResolver,
      final EventFilterResolver eventFilterResolver,
      final EncryptionService encryptionService) {
    this.patientFilterFromRequestParamResolver = patientFilterFromRequestParamResolver;
    this.eventFilterResolver = eventFilterResolver;
    this.encryptionService = encryptionService;
    this.searchRedirect = searchRedirect;
  }

  /**
   * Intercepts legacy home page search requests, pulls out the current user from the JSESSIONID, the search criteria
   * from the incomingParams map, and forwards the request to the modernization search page
   */
  @Hidden
  @PostMapping("/nbs/redirect/simpleSearch")
  RedirectView redirectSimpleSearch(
      final RedirectAttributes attributes,
      @RequestParam final Map<String, String> incomingParams) {
    var redirect = new RedirectView(searchRedirect.base());
    var redirectedUrl = redirect.getUrl();
    if (redirectedUrl != null && redirectedUrl.equals(searchRedirect.base()) && !incomingParams.isEmpty()) {

      // Event filter takes precedence
      var eventFilter = eventFilterResolver.resolve(incomingParams);
      if (eventFilter != null) {
        attributes.addAttribute("q", encryptionService.handleEncryption(eventFilter));
        String type = eventFilter instanceof InvestigationFilter ? "investigation" : "labReport";
        attributes.addAttribute("type", type);
      } else {
        var patientFilter = patientFilterFromRequestParamResolver.resolve(incomingParams);
        attributes.addAttribute("q", encryptionService.handleEncryption(patientFilter));
      }
    }
    return redirect;
  }

  /**
   * Intercepts legacy advanced search page requests, pulls out the current user from the JSESSIONID, and forwards the
   * request to the modernization search page
   */
  @Hidden
  @GetMapping("/nbs/redirect/advancedSearch")
  RedirectView redirectAdvancedSearch() {
    return new RedirectView(searchRedirect.base());
  }
}
