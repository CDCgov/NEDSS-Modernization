package gov.cdc.nbs.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import gov.cdc.nbs.authentication.NbsAuthority;
import gov.cdc.nbs.authentication.NbsUserDetails;
import gov.cdc.nbs.authentication.UserService;
import gov.cdc.nbs.authentication.config.SecurityProperties;
import gov.cdc.nbs.authentication.entity.AuthUser;
import gov.cdc.nbs.authentication.entity.AuthUserRepository;
import gov.cdc.nbs.graphql.GraphQLPage;
import gov.cdc.nbs.model.LoginRequest;
import gov.cdc.nbs.model.LoginResponse;

@RestController
public class UserController {
    private static final String TOKEN_COOKIE_NAME = "nbs_token";

    @Autowired
    private UserService userService;
    @Autowired
    private SecurityProperties securityProperties;
    @Autowired
    private AuthUserRepository userRepository;

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

    /**
     * Returns a page of users that share a program area with the current user, logic copied from legacy NBS -
     * DbAuthDAOImpl.java "getSecureUserDTListBasedOnProgramArea"
     */
    @QueryMapping
    public Page<AuthUser> findAllUsers(@Argument GraphQLPage page) {
        var loggedInUser = (NbsUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var userProgramAreas = loggedInUser.getAuthorities()
                .stream()
                .map(NbsAuthority::getProgramArea)
                .distinct()
                .toList();
        return userRepository.findByProgramAreas(userProgramAreas, GraphQLPage.toPageable(page, maxPageSize));
    }

}
