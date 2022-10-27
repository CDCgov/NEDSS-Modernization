package gov.cdc.nbs.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@Controller
public class RedirectController {

    @GetMapping("/nbs/MyTaskList1.do")
    public RedirectView redirectAdvancedSearch(HttpServletRequest request, RedirectAttributes attributes) {
        request.getContentLength();
        var headerNames = request.getHeaderNames();
        if (headerNames != null) {
            while (headerNames.hasMoreElements()) {
                System.out.println("Header: " + request.getHeader(headerNames.nextElement()));
            }
        }
        attributes.addAttribute("attribute", "advancedSearchRedirect");
        return new RedirectView("/search");
    }

    @PostMapping("/nbs/HomePage.do") // ?method=patientSearchSubmit
    public RedirectView redirectSimpleSearch(HttpServletRequest request, RedirectAttributes attributes,
            @RequestParam Map<String, String> body) {

        System.out.println("Body of request:");
        System.out.println(body);
        request.getContentLength();
        var headerNames = request.getHeaderNames();
        if (headerNames != null) {
            while (headerNames.hasMoreElements()) {
                System.out.println("Header: " + request.getHeader(headerNames.nextElement()));
            }
        }
        attributes.addAttribute("attribute", "simpleSearchRedirect");
        return new RedirectView("/search");
    }
}
