package gov.cdc.nbs.option.users.list;

import gov.cdc.nbs.option.Option;
import io.swagger.v3.oas.annotations.Operation;
import java.util.Collection;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class UserOptionListController {
  private final UserOptionFinder finder;

  UserOptionListController(final UserOptionFinder finder) {
    this.finder = finder;
  }

  @Operation(
      operationId = "user-list",
      summary = "NBS User List",
      description = "Provides all users in NBS.",
      tags = "UserOptions")
  @GetMapping("/nbs/api/options/users")
  Collection<Option> users() {
    return finder.find();
  }
}
