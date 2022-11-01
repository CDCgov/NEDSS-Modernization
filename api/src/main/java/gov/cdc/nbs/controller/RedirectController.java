package gov.cdc.nbs.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
    public RedirectView redirectSimpleSearch(
            HttpServletRequest request,
            HttpServletResponse response,
            RedirectAttributes attributes,
            @RequestParam Map<String, String> incomingParams) throws IOException {
        var user = redirectionService.getUserFromSession(request.getCookies());
        if (user.isPresent()) {
            addUserIdCookie(user.get().getUserId(), response);
            attributes.addAllAttributes(redirectionService.getSearchAttributes(incomingParams));
            return new RedirectView("/");
        } else {
            return new RedirectView("/nbs/timeout");
        }
    }

    @ApiIgnore
    @GetMapping("/nbs/MyTaskList1.do") // proxy verifies path contains: ?ContextAction=GlobalPatient
    public RedirectView redirectAdvancedSearch(
            HttpServletRequest request,
            HttpServletResponse response,
            RedirectAttributes attributes) throws IOException {
        var user = redirectionService.getUserFromSession(request.getCookies());
        if (user.isPresent()) {
            addUserIdCookie(user.get().getUserId(), response);
            return new RedirectView("/search");
        } else {
            return new RedirectView("/nbs/timeout");
        }
    }

    private void addUserIdCookie(String userId, HttpServletResponse response) {
        var cookie = new Cookie("nbsUserId", userId);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

}
