package gov.cdc.nbs.codes.user;

import gov.cdc.nbs.authentication.NbsUserDetails;
import gov.cdc.nbs.graphql.GraphQLPage;
import gov.cdc.nbs.graphql.GraphQLPageableMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

@Controller
class UserListItemResolver {

  private final GraphQLPageableMapper mapper;
  private final UserListItemFinder finder;

  UserListItemResolver(
      final GraphQLPageableMapper mapper,
      final UserListItemFinder finder
  ) {
    this.mapper = mapper;
    this.finder = finder;
  }


  @QueryMapping(name = "findAllUsers")
  Page<UserListItem> resolve(
      @AuthenticationPrincipal final NbsUserDetails user,
      @Argument final GraphQLPage page
  ) {
    Pageable pageable = mapper.from(page);

    return finder.find(user.getId(), pageable);
  }

}
