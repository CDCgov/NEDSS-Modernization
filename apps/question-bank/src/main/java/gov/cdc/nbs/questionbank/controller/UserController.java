package gov.cdc.nbs.questionbank.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import gov.cdc.nbs.config.security.SecurityProperties;
import gov.cdc.nbs.model.LoginRequest;
import gov.cdc.nbs.model.LoginResponse;
import gov.cdc.nbs.service.UserService;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class UserController {
    private static final String TOKEN_COOKIE_NAME = "nbs_token";

    @Autowired
    private UserService userService;
    @Autowired
    private SecurityProperties securityProperties;

    @Value("${nbs.max-page-size: 50}")
    private Integer maxPageSize;

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