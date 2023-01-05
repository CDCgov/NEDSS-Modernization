package gov.cdc.nbs.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import gov.cdc.nbs.service.EncryptionService;
import gov.cdc.nbs.service.RedirectionService;
import io.swagger.annotations.ApiImplicitParam;
import springfox.documentation.annotations.ApiIgnore;

@RestController
public class RedirectController {
    @Autowired
    private RedirectionService redirectionService;
    @Autowired
    private EncryptionService encryptionService;
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${nbs.wildfly.url:http://wildfly:7001}")
    private String wildFlyUrl;

    @ApiIgnore
    @PostMapping("/nbs/HomePage.do") // proxy verifies path contains: ?method=patientSearchSubmit
    public RedirectView redirectSimpleSearch(
            HttpServletRequest request,
            HttpServletResponse response,
            RedirectAttributes attributes,
            @RequestParam Map<String, String> incomingParams) throws IOException {
        var redirect = redirectionService.handleRedirect("/advanced-search", request, response);
        var redirectedUrl = redirect.getUrl();
        if (redirectedUrl != null && redirectedUrl.equals("/advanced-search")) {
            if (incomingParams.size() > 0) {
                var patientFilter = redirectionService.getPatientFilterFromParams(incomingParams);
                var encryptedFilter = encryptionService.handleEncryption(patientFilter);
                attributes.addAttribute("q", encryptedFilter);
            }
        }
        return redirect;
    }

    @ApiIgnore
    @GetMapping("/nbs/MyTaskList1.do") // proxy verifies path contains: ?ContextAction=GlobalPatient
    public RedirectView redirectAdvancedSearch(
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        return redirectionService.handleRedirect("/advanced-search", request, response);
    }

    /**
     * Sends a GET request to
     * <WildFly_URL>/nbs/HomePage.do?method=patientSearchSubmit
     * to set up the session variables so that we can navigate directly to Add
     * Patient or Patient Details pages
     */
    @GetMapping("/preparePatientDetails")
    @ApiImplicitParam(name = "Authorization", required = true, allowEmptyValue = false, paramType = "header")
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
