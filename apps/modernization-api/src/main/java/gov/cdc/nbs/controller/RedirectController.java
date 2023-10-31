package gov.cdc.nbs.controller;

import gov.cdc.nbs.event.search.InvestigationFilter;
import gov.cdc.nbs.redirect.search.EventFilterResolver;
import gov.cdc.nbs.redirect.search.PatientFilterFromRequestParamResolver;
import gov.cdc.nbs.service.EncryptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

@RestController
public class RedirectController {
    private static final String ADVANCED_SEARCH = "/advanced-search";

    @Autowired
    PatientFilterFromRequestParamResolver patientFilterFromRequestParamResolver;

    @Autowired
    EventFilterResolver eventFilterResolver;

    @Autowired
    EncryptionService encryptionService;

    /**
     * Intercepts legacy home page search requests, pulls out the current user from the JSESSIONID, the search criteria
     * from the incomingParams map, and forwards the request to the modernization search page
     */
    @ApiIgnore
    @PostMapping("/nbs/redirect/simpleSearch")
    public RedirectView redirectSimpleSearch(
            final RedirectAttributes attributes,
            @RequestParam final Map<String, String> incomingParams) {
        var redirect = new RedirectView(ADVANCED_SEARCH);
        var redirectedUrl = redirect.getUrl();
        if (redirectedUrl != null && redirectedUrl.equals(ADVANCED_SEARCH) && incomingParams.size() > 0) {

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
    @ApiIgnore
    @GetMapping("/nbs/redirect/advancedSearch")
    public RedirectView redirectAdvancedSearch() {
        return new RedirectView(ADVANCED_SEARCH);
    }

    @ApiIgnore
    @GetMapping("/nbs/redirect/pagebuilder/manage/pages")
    public RedirectView redirectManagePages() {
        return new RedirectView("/page-builder/manage/pages");
    }

}
