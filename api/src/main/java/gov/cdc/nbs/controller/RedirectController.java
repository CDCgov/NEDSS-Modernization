package gov.cdc.nbs.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import gov.cdc.nbs.service.EncryptionService;
import gov.cdc.nbs.service.RedirectionService;
import lombok.AllArgsConstructor;
import springfox.documentation.annotations.ApiIgnore;

@Controller
@AllArgsConstructor
public class RedirectController {
    private final RedirectionService redirectionService;
    private final EncryptionService encryptionService;

    @ApiIgnore
    @PostMapping("/nbs/HomePage.do") // proxy verifies path contains: ?method=patientSearchSubmit
    public RedirectView redirectSimpleSearch(
            HttpServletRequest request,
            HttpServletResponse response,
            RedirectAttributes attributes,
            @RequestParam Map<String, String> incomingParams) throws IOException {
        var redirect = redirectionService.handleRedirect("/search", request, response);
        var redirectedUrl = redirect.getUrl();
        if (redirectedUrl != null && redirectedUrl.equals("/search")) {
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

}
