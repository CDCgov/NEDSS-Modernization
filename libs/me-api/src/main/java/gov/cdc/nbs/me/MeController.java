package gov.cdc.nbs.me;

import gov.cdc.nbs.authentication.NbsUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class MeController {

  @Operation(
      operationId = "Me",
      summary = "Current User",
      description = "Provides details about the user associated with the request.",
      tags = "User")
  @GetMapping("nbs/api/me")
  Me me(@Parameter(hidden = true) @AuthenticationPrincipal final NbsUserDetails details) {

    List<String> permissions =
        details.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

    return new Me(details.getId(), details.getFirstName(), details.getLastName(), permissions);
  }
}
