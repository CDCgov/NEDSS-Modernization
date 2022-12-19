package gov.cdc.nbs.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import gov.cdc.nbs.config.security.SecurityProperties;
import gov.cdc.nbs.entity.odse.AuthUser;
import gov.cdc.nbs.graphql.GraphQLPage;
import gov.cdc.nbs.model.LoginRequest;
import gov.cdc.nbs.model.LoginResponse;
import gov.cdc.nbs.repository.AuthUserRepository;
import gov.cdc.nbs.service.UserService;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private SecurityProperties securityProperties;
    @Autowired
    private AuthUserRepository userRepository;
    private final String TOKEN_COOKIE_NAME = "nbs_token";

    @Value("${nbs.max-page-size: 50}")
    private Integer MAX_PAGE_SIZE;

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

    @QueryMapping
    public Page<AuthUser> findAllUsers(@Argument GraphQLPage page) {
        return userRepository.findAll(GraphQLPage.toPageable(page, MAX_PAGE_SIZE));
    }

}
