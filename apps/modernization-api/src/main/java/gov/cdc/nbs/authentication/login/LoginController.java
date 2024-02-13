package gov.cdc.nbs.authentication.login;

import gov.cdc.nbs.authentication.NBSToken;
import gov.cdc.nbs.authentication.TokenCreator;
import gov.cdc.nbs.authentication.UserService;
import gov.cdc.nbs.authentication.config.SecurityProperties;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
class LoginController {
  private final UserService userService;
  private final SecurityProperties securityProperties;
  private final TokenCreator creator;

  LoginController(
      final UserService userService,
      final SecurityProperties securityProperties,
      final TokenCreator creator
  ) {
    this.userService = userService;
    this.securityProperties = securityProperties;
    this.creator = creator;
  }

  @Operation(
      operationId = "login",
      summary = "NBS User Authentication",
      description = "Provides options from Users that have a name matching a criteria.",
      tags = "Login"
  )
  @ApiOperation(value = "NBS User Authentication", nickname = "login", tags = "Login")
  @PostMapping("/login")
  LoginResponse login(@RequestBody LoginRequest request, HttpServletResponse response) {
    var userDetails = userService.loadUserByUsername(request.username());

    NBSToken token = this.creator.forUser(request.username());

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
