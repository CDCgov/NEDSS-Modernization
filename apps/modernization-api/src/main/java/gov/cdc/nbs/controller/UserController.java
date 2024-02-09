package gov.cdc.nbs.controller;

import gov.cdc.nbs.authentication.NBSToken;
import gov.cdc.nbs.authentication.TokenCreator;
import gov.cdc.nbs.authentication.UserService;
import gov.cdc.nbs.authentication.config.SecurityProperties;
import gov.cdc.nbs.model.LoginRequest;
import gov.cdc.nbs.model.LoginResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
class UserController {
  private final UserService userService;
  private final SecurityProperties securityProperties;
  private final TokenCreator creator;

  UserController(
      final UserService userService,
      final SecurityProperties securityProperties,
      final TokenCreator creator
  ) {
    this.userService = userService;
    this.securityProperties = securityProperties;
    this.creator = creator;
  }

  @PostMapping("/login")
  LoginResponse login(@RequestBody LoginRequest request, HttpServletResponse response) {
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

}
