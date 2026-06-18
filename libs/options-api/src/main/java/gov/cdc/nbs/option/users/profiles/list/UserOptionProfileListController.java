package gov.cdc.nbs.option.users.profiles.list;

import gov.cdc.nbs.option.Option;
import io.swagger.v3.oas.annotations.Operation;
import java.util.Collection;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class UserOptionProfileListController {
  private final UserProfileOptionFinder finder;

  UserOptionProfileListController(final UserProfileOptionFinder finder) {
    this.finder = finder;
  }

  @Operation(
      operationId = "user-profile-list",
      summary = "NBS User Profile List",
      description = "Provides all user profiles in NBS.",
      tags = "UserOptions")
  @GetMapping("/nbs/api/options/users/profiles")
  Collection<Option> userProfiles() {
    return finder.find();
  }
}
