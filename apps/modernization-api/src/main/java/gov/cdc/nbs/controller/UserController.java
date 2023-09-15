package gov.cdc.nbs.controller;

import gov.cdc.nbs.authentication.NBSToken;
import gov.cdc.nbs.authentication.NbsAuthority;
import gov.cdc.nbs.authentication.NbsUserDetails;
import gov.cdc.nbs.authentication.TokenCreator;
import gov.cdc.nbs.authentication.UserService;
import gov.cdc.nbs.authentication.config.SecurityProperties;
import gov.cdc.nbs.authentication.entity.AuthUser;
import gov.cdc.nbs.authentication.entity.AuthUserRepository;
import gov.cdc.nbs.graphql.GraphQLPage;
import gov.cdc.nbs.model.LoginRequest;
import gov.cdc.nbs.model.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    SecurityProperties securityProperties;
    @Autowired
    AuthUserRepository userRepository;
    @Autowired
    TokenCreator creator;

    @Value("${nbs.max-page-size: 50}")
    private Integer maxPageSize;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request, HttpServletResponse response) {
        var userDetails = userService.loadUserByUsername(request.getUsername());

        NBSToken token = this.creator.forUser(request.getUsername());

        token.apply(
            securityProperties,
            response
        );

        return new LoginResponse(
            userDetails.getId(),
            userDetails.getUsername(),
            userDetails.getFirstName() + " " + userDetails.getLastName(),
            token.value()
        );
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
