package gov.cdc.nbs.authentication.login;

import gov.cdc.nbs.authentication.NBSToken;
import gov.cdc.nbs.authentication.SecurityProperties;
import gov.cdc.nbs.authentication.TokenCreator;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class LoginController {
  private final UserDetailsService userService;
  private final SecurityProperties securityProperties;
  private final TokenCreator creator;

  LoginController(
      final UserDetailsService userService,
      final SecurityProperties securityProperties,
      final TokenCreator creator) {
    this.userService = userService;
    this.securityProperties = securityProperties;
    this.creator = creator;
  }

  @Operation(
      operationId = "login",
      summary = "NBS User Authentication",
      description = "Provides options from Users that have a name matching a criteria.",
      tags = "Login")
  @PostMapping("/login")
  LoginResponse login(@RequestBody LoginRequest request, HttpServletResponse response) {
    var userDetails = userService.loadUserByUsername(request.username());

    NBSToken token = this.creator.forUser(request.username());

    token.apply(securityProperties, response);

    return new LoginResponse(userDetails.getUsername(), token.value());
  }
}
