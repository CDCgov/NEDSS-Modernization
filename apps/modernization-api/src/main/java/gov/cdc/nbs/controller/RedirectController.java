package gov.cdc.nbs.controller;

import gov.cdc.nbs.redirect.search.PatientFilterFromRequestParamResolver;
import gov.cdc.nbs.service.EncryptionService;
import gov.cdc.nbs.service.RedirectionService;
import io.swagger.annotations.ApiImplicitParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
public class RedirectController {
  private static final String ADVANCED_SEARCH = "/advanced-search";

  @Autowired
  PatientFilterFromRequestParamResolver patientFilterFromRequestParamResolver;

  @Autowired
  private RedirectionService redirectionService;
  @Autowired
  private EncryptionService encryptionService;
  private final RestTemplate restTemplate = new RestTemplate();

  @Value("${nbs.wildfly.url:http://wildfly:7001}")
  private String wildFlyUrl;

  /**
   * Intercepts legacy home page search requests, pulls out the current user from the JSESSIONID, the search criteria
   * from the incomingParams map, and forwards the request to the modernization search page
   */
  @ApiIgnore
  @PostMapping("/nbs/redirect/simpleSearch")
  public RedirectView redirectSimpleSearch(
      HttpServletRequest request,
      HttpServletResponse response,
      RedirectAttributes attributes,
      @RequestParam Map<String, String> incomingParams) {
    var redirect = redirectionService.handleRedirect(ADVANCED_SEARCH, request, response);
    var redirectedUrl = redirect.getUrl();
    if (redirectedUrl != null && redirectedUrl.equals(ADVANCED_SEARCH) && incomingParams.size() > 0) {
      var patientFilter = patientFilterFromRequestParamResolver.resolve(incomingParams);
      var encryptedFilter = encryptionService.handleEncryption(patientFilter);
      attributes.addAttribute("q", encryptedFilter);
    }
    return redirect;
  }

  /**
   * Intercepts legacy advanced search page requests, pulls out the current user from the JSESSIONID, and forwards the
   * request to the modernization search page
   */
  @ApiIgnore
  @GetMapping("/nbs/redirect/advancedSearch")
  public RedirectView redirectAdvancedSearch(
      HttpServletRequest request,
      HttpServletResponse response) {
    return redirectionService.handleRedirect(ADVANCED_SEARCH, request, response);
  }

  /**
   * Sends a GET request to
   * <WildFly_URL>/nbs/HomePage.do?method=patientSearchSubmit
   * to set up the session variables so that we can navigate directly to Add Patient or Patient Details pages
   */
  @GetMapping("/preparePatientDetails")
  @ApiImplicitParam(
            name = "Authorization",
            required = true,
            allowEmptyValue = false,
            paramType = "header",
            dataTypeClass = String.class)
  public void preparePatientDetails(HttpServletRequest request) {
    String url = wildFlyUrl + "/nbs/HomePage.do?method=patientSearchSubmit";
    // copy cookie header that contains the JSESSIONID from the original request
    HttpHeaders headers = new HttpHeaders();
    headers.add("cookie", request.getHeader("cookie"));
    // send an empty search request to wildfly
    var emptySearchRequest = RequestEntity.get(url).headers(headers).accept(MediaType.ALL).build();
    restTemplate.exchange(emptySearchRequest, String.class);
  }

}
