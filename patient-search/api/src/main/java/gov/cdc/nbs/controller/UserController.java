package gov.cdc.nbs.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import gov.cdc.nbs.config.security.SecurityProperties;
import gov.cdc.nbs.model.LoginRequest;
import gov.cdc.nbs.model.LoginResponse;
import gov.cdc.nbs.service.UserService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
public class UserController {
    private final UserService userService;
    private final SecurityProperties securityProperties;
    private final String TOKEN_COOKIE_NAME = "nbs_token";

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request, HttpServletResponse response) {
        var userDetails = userService.loadUserByUsername(request.getUsername());
        var cookie = new Cookie(TOKEN_COOKIE_NAME, userDetails.getToken());
        cookie.setMaxAge(securityProperties.getTokenExpirationSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);
        return new LoginResponse(userDetails.getUsername(),
                userDetails.getFirstName() + " " + userDetails.getLastName(),
                userDetails.getToken());
    }

}
