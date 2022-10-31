package gov.cdc.nbs.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import gov.cdc.nbs.service.RedirectionService;
import lombok.AllArgsConstructor;
import springfox.documentation.annotations.ApiIgnore;

@Controller
@AllArgsConstructor
public class RedirectController {
    private final RedirectionService redirectionService;

    @ApiIgnore
    @PostMapping("/nbs/HomePage.do") // proxy verifies path contains: ?method=patientSearchSubmit
    public RedirectView redirectSimpleSearch(HttpServletRequest request, RedirectAttributes attributes,
            @RequestParam Map<String, String> incomingParams) {
        attributes.addAllAttributes(redirectionService.getSearchAttributes(incomingParams));
        return new RedirectView("/");
    }

    @ApiIgnore
    @GetMapping("/nbs/MyTaskList1.do")
    public RedirectView redirectAdvancedSearch(HttpServletRequest request, RedirectAttributes attributes) {
        return new RedirectView("/search");
    }

}
